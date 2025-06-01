package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/login.html",
                    "/dashboard.html",
                    "/js/**",
                    "/css/**",
                    "/api/users/register",
                    "/api/users/login"
                ).permitAll()
                .anyRequest().authenticated()
            )
            .httpBasic(HttpBasicConfigurer::disable); // no formLogin()

        return http.build();
    }
}



