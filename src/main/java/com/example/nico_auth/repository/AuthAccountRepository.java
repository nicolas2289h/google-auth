package com.example.nico_auth.repository;

import com.example.nico_auth.user.AuthAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthAccountRepository extends JpaRepository<AuthAccount, Long> {
    Optional<AuthAccount> findByEmail(String email);
}
