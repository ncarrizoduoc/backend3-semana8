package com.duoc.auth_server.security;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.duoc.auth_server.service.JwtTokenService;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component 
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtTokenService jwtTokenService;
    private final ObjectMapper objectMapper;

    public OAuth2LoginSuccessHandler(JwtTokenService jwtTokenService, ObjectMapper objectMapper) {
        this.jwtTokenService = jwtTokenService;
        this.objectMapper = objectMapper;
    }

    // Método que se ejecuta cuando la autenticación por OAuth2 es exitosa
    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        // Token recibido desde GitHub después de la autenticación exitosa
        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;

        // Obtener información del usuario autenticado desde GitHub
        OAuth2User githubUser = oauthToken.getPrincipal();

        String githubLogin = githubUser.getAttribute("login");
        String email = githubUser.getAttribute("email");
        String name = githubUser.getAttribute("name");

        // Generar token con datos de usuario, para comunicación con microservicios
        String jwt = jwtTokenService.generateToken(
                githubLogin,
                email,
                name);

        // Generar respuesta con token JWT y datos del usuario
        Map<String, Object> tokenResponse = new LinkedHashMap<>();
        tokenResponse.put("tokenType", "Bearer");
        tokenResponse.put("accessToken", jwt);
        tokenResponse.put("expiresIn", 3600);
        tokenResponse.put("githubUser", githubLogin);
        tokenResponse.put("email", email);
        tokenResponse.put("name", name); // Nombre de usuario
        
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        objectMapper.writeValue(
                response.getOutputStream(),
                tokenResponse);

    }

}
