package com.key_stone.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.userdetails.UserDetails;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtService jwtService;
	private final UserDetailsServiceImpl userDetailsService;

	public JwtAuthenticationFilter(
	        JwtService jwtService,
	        UserDetailsServiceImpl userDetailsService) {

	    this.jwtService = jwtService;
	    this.userDetailsService = userDetailsService;
	}
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String authorizationHeader =
                request.getHeader("Authorization");

        if (authorizationHeader == null ||
                !authorizationHeader.startsWith("Bearer ")) {

            filterChain.doFilter(request, response);
            return;
        }

        String token =
                authorizationHeader.substring(7);

        try {

        	String username =
        	        jwtService.extractUsername(token);

        	if (username != null &&
        	        SecurityContextHolder.getContext()
        	                .getAuthentication() == null) {

        	    UserDetails userDetails =
        	            userDetailsService.loadUserByUsername(username);

        	    boolean validToken =
        	            jwtService.isTokenValid(
        	                    token,
        	                    userDetails.getUsername()
        	            );

        	    if (validToken) {

        	        UsernamePasswordAuthenticationToken authentication =
        	                new UsernamePasswordAuthenticationToken(
        	                        userDetails,
        	                        null,
        	                        userDetails.getAuthorities()
        	                );

        	        SecurityContextHolder
        	                .getContext()
        	                .setAuthentication(authentication);
        	    }
        	} 

        } catch (Exception exception) {

            System.out.println(
                    "Invalid JWT: " + exception.getMessage()
            );
        }

        filterChain.doFilter(request, response);
    }
}
