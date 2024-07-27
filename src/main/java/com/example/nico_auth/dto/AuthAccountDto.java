package com.example.nico_auth.dto;

import com.example.nico_auth.user.AuthAccount;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthAccountDto {

    private String firstName;

    private String lastName;

    private String email;

    private String roles;

    public static final AuthAccountDto convertToDto(AuthAccount account) {
        return AuthAccountDto.builder()
                .firstName(account.getFirstName())
                .lastName(account.getLastName())
                .email(account.getEmail())
                .roles(account.getRoles())
                .build();
    }
}
