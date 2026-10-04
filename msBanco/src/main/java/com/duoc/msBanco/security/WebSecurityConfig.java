package com.duoc.msBanco.security;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration 
@EnableWebSecurity 
public class WebSecurityConfig {

    @Bean 
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            // Sesión sin estado (no se almacenan datos de sesión en el servidor).
            // El usuario debe proveer un JWT para autenticarse en cada solicitud
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/actuator/**").permitAll()
                .anyRequest().authenticated()
            )
            .oauth2ResourceServer(oauth2 -> oauth2
                .jwt(withDefaults())
            )
            .httpBasic(AbstractHttpConfigurer::disable) // Inhabilitar autenticación básica
            .formLogin(AbstractHttpConfigurer::disable) // Inhabilitar formulario de login
            .logout(AbstractHttpConfigurer::disable); // Inhabilitar logout
        return http.build(); 
    }

}
