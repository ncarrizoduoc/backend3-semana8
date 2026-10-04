package com.duoc.auth_server.service;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

@Service
public class JwtTokenService {

    private final JwtEncoder jwtEncoder;
    private final String issuer;
    private final long expirationSeconds;

    public JwtTokenService(
            JwtEncoder jwtEncoder,
            @Value("${ms.auth.jwt.issuer}") String issuer,
            @Value("${ms.auth.jwt.expiration-seconds}") long expirationSeconds) {

        this.jwtEncoder = jwtEncoder;
        this.issuer = issuer;
        this.expirationSeconds = expirationSeconds;
    }

    // Generar token JWT
    public String generateToken(String githubLogin, String email, String name) {
        Instant now = Instant.now();

        // Agregar claims al token JWT
        JwtClaimsSet.Builder claimsBuilder = JwtClaimsSet.builder()
                .issuer(issuer)
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expirationSeconds))
                .subject(githubLogin) // Nombre de usuario autenticado de GitHub como subject
                .claim("provider", "github")
                .claim("scope", "citas.read citas.write");

        // Si la respuesta de GitHub incluye email y name, agregarlos como claims adicionales
        if (email != null && !email.isEmpty()) {
            claimsBuilder.claim("email", email);
        }

        if (name != null && !name.isEmpty()) {
            claimsBuilder.claim("name", name);
        }

        JwtClaimsSet claims = claimsBuilder.build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

}
