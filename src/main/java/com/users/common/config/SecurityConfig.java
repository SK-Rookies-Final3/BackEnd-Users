package com.users.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowedOrigins(List.of("*"));
                    config.setAllowedMethods(List.of("GET", "POST", "DELETE", "OPTIONS", "PATCH"));
                    config.setAllowedHeaders(List.of("*"));
                    config.setAllowCredentials(true);
                    return config;
                }))
                .csrf(csrf -> csrf.disable()) // CSRF 비활성화
                .authorizeExchange(authz -> authz // 리액티브에서는 authorizeExchange 사용
                        .pathMatchers("/update").authenticated()
                        .anyExchange().permitAll()
                );
        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}