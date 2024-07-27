package com.example.nico_auth.service;

import com.example.nico_auth.config.JWTUtils;
import com.example.nico_auth.dto.IdTokenRequestDto;
import com.example.nico_auth.repository.AuthAccountRepository;
import com.example.nico_auth.user.AuthAccount;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Service
public class AuthAccountService {

    private final AuthAccountRepository accountRepository;
    private final JWTUtils jwtUtils;
    private final GoogleIdTokenVerifier verifier;

    public AuthAccountService(@Value("${app.googleClientId}") String clientId, AuthAccountRepository accountRepository,
                              JWTUtils jwtUtils) {
        this.accountRepository = accountRepository;
        this.jwtUtils = jwtUtils;
        NetHttpTransport transport = new NetHttpTransport();
        JsonFactory jsonFactory = new JacksonFactory();
        verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                .setAudience(Collections.singletonList(clientId))
                .build();
    }

    public AuthAccount getAccount(Long id) {
        return accountRepository.findById(id).orElse(null);
    }

    public String loginOAuthGoogle(IdTokenRequestDto requestBody) {
        AuthAccount account = verifyIDToken(requestBody.getIdToken());
        if (account == null) {
            throw new IllegalArgumentException();
        }
        account = createOrUpdateUser(account);
        return jwtUtils.createToken(account, false);
    }

    @Transactional
    public AuthAccount createOrUpdateUser(AuthAccount account) {
        AuthAccount existingAccount = accountRepository.findByEmail(account.getEmail()).orElse(null);
        if (existingAccount == null) {
            account.setRoles("ROLE_USER");
            accountRepository.save(account);
            return account;
        }
        existingAccount.setFirstName(account.getFirstName());
        existingAccount.setLastName(account.getLastName());
        existingAccount.setPictureUrl(account.getPictureUrl());
        accountRepository.save(existingAccount);
        return existingAccount;
    }

    private AuthAccount verifyIDToken(String idToken) {
        try {
            GoogleIdToken idTokenObj = verifier.verify(idToken);
            if (idTokenObj == null) {
                return null;
            }
            GoogleIdToken.Payload payload = idTokenObj.getPayload();
            String firstName = (String) payload.get("given_name");
            String lastName = (String) payload.get("family_name");
            String email = payload.getEmail();
            String pictureUrl = (String) payload.get("picture");
            String role = (String) payload.get("role");
            return new AuthAccount(firstName, lastName, email, pictureUrl, role);
        } catch (GeneralSecurityException | IOException e) {
            return null;
        }
    }
}
