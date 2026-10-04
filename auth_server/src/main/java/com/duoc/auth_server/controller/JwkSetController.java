package com.duoc.auth_server.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nimbusds.jose.jwk.JWKSet;

@RestController 
public class JwkSetController {

    private final JWKSet jwkSet;

    public JwkSetController(JWKSet jwkSet) {
        this.jwkSet = jwkSet;
    }

    // Endpoint para exponer las claves públicas en formato JWK (JSON Web Key Set)
    @GetMapping("/.well-known/jwks.json")
    public Map<String, Object> getPublicKeys() {
        return jwkSet.toJSONObject();
    }

}
