package com.br.desafiorocket.desafio_rocket.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/curso/").permitAll()
                    .requestMatchers("/curso/all").permitAll()
                    .requestMatchers("/curso/search").permitAll()
                    .requestMatchers("/curso/{id}/active").permitAll()
                            .anyRequest().authenticated();
                });

        return http.build();
    }

}
