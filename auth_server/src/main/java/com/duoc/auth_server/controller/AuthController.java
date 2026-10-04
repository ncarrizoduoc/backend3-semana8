package com.duoc.auth_server.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController 
@RequestMapping("/api/auth")
public class AuthController {

    @GetMapping("/login")
    public ResponseEntity<Map<String, String>> login() {
        return ResponseEntity.ok(Map.of(
            "message", "Para autenticarse con GitHub ingrese a la URL /oauth2/authorization/github",
            "authorizationURL", "Http://localhost:8080/oauth2/authorization/github" // URL generada automáticamente por Spring Security para iniciar el flujo de autenticación con GitHub
        ));
    }

}
