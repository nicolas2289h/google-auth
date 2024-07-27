package com.example.nico_auth.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "account")
public class AuthAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String pictureUrl;

    private String roles;

    public AuthAccount(String firstName, String lastName, String email, String pictureUrl, String role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.pictureUrl = pictureUrl;
        this.roles = role;
    }
}
