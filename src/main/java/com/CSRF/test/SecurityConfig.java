package com.CSRF.test;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRepository;


@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public CsrfTokenRepository csrfTokenRepository() {
        return CookieCsrfTokenRepository.withHttpOnlyFalse();
    }

    @Configuration
    public static class WebSecurityConfig {

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http, CsrfTokenRepository csrfTokenRepository) throws Exception {
            http
                    .csrf(csrf -> csrf.csrfTokenRepository(csrfTokenRepository))
                    .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .authorizeRequests(authorize -> authorize.requestMatchers("/login/**","/home/**","/change-password/**").permitAll().anyRequest().authenticated());

            return http.build();
        }

    }
}