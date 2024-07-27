package com.example.nico_auth.controller;

import com.example.nico_auth.service.AuthAccountService;
import com.example.nico_auth.user.AuthAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

import static com.example.nico_auth.dto.AuthAccountDto.convertToDto;

@RestController
@RequestMapping("/v1/oauth")
public class AuthController {

    @Autowired
    AuthAccountService accountService;

    @GetMapping("/user/info")
    public ResponseEntity getUserInfo(Principal principal) {
        AuthAccount account = accountService.getAccount(Long.valueOf(principal.getName()));
        return ResponseEntity.ok().body(convertToDto(account));
    }
}
