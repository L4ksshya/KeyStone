package com.key_stone.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

import org.springframework.http.HttpMethod;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())

            .sessionManagement(session ->
                session.sessionCreationPolicy(
                    SessionCreationPolicy.STATELESS
                )
            )

            .authorizeHttpRequests(auth -> auth
            		.requestMatchers("/api/auth/**").permitAll()

                    .requestMatchers(HttpMethod.GET, "/api/users/**")
                    .hasAnyAuthority("USER", "ADMIN")
                    
                    .requestMatchers(HttpMethod.PATCH, "/api/users/*/password")
                    .authenticated()

                    .requestMatchers(HttpMethod.POST, "/api/users/**")
                    .hasAuthority("ADMIN")

                    .requestMatchers(HttpMethod.PUT, "/api/users/**")
                    .hasAuthority("ADMIN")

                    .requestMatchers(HttpMethod.DELETE, "/api/users/**")
                    .hasAuthority("ADMIN")
                    
                    .requestMatchers(HttpMethod.GET, "/api/customers/**")
                    .hasAnyAuthority("USER", "ADMIN")

                    .requestMatchers(HttpMethod.POST, "/api/customers/**")
                    .hasAuthority("ADMIN")
                    
                    .requestMatchers(HttpMethod.PUT, "/api/customers/**")
                    .hasAuthority("ADMIN")

                    .requestMatchers(HttpMethod.DELETE, "/api/customers/**")
                    .hasAuthority("ADMIN")
                    
                    .requestMatchers(HttpMethod.GET, "/api/technicians/**")
                    .hasAnyAuthority("USER", "ADMIN")

                .requestMatchers(HttpMethod.POST, "/api/technicians/**")
                    .hasAuthority("ADMIN")
                    
                    .requestMatchers(HttpMethod.PUT, "/api/technicians/**")
                    .hasAuthority("ADMIN")
                    
                    .requestMatchers(HttpMethod.DELETE, "/api/technicians/**")
                    .hasAuthority("ADMIN")
                    
                    .requestMatchers(HttpMethod.GET, "/api/service-requests/**")
                    .hasAnyAuthority("USER", "ADMIN")

                .requestMatchers(HttpMethod.POST, "/api/service-requests/**")
                    .hasAnyAuthority("USER", "ADMIN")
                    
                    .requestMatchers(HttpMethod.PATCH, "/api/service-requests/*/status")
                    .hasAuthority("ADMIN")
                    
                    .requestMatchers(HttpMethod.PUT, "/api/service-requests/**")
                    .hasAuthority("ADMIN")

                    .anyRequest().authenticated()
            );

        http.addFilterBefore(
                jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class
        );

        return http.build();
    }
}