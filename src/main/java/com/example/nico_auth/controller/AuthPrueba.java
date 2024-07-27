package com.example.nico_auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


@RestController
public class AuthPrueba {

    @GetMapping("/protegido")
    public ResponseEntity<Map<String, String>> holaProtegido(){
        Map<String, String> response = new HashMap<>();
        response.put("message", "hola");
        return ResponseEntity.ok(response);
    }
}
