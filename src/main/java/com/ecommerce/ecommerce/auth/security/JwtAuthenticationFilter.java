package com.ecommerce.ecommerce.auth.security;
import com.ecommerce.ecommerce.auth.modal.User;
import com.ecommerce.ecommerce.auth.service.CustomUserDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtConfig jwtConfig;
    private final CustomUserDetailsService customUserDetailsService;

    public JwtAuthenticationFilter(
            AuthenticationManager authenticationManager,
            JwtConfig jwtConfig,
            CustomUserDetailsService customUserDetailsService
    ) {
        this.authenticationManager = authenticationManager;
        this.jwtConfig = jwtConfig;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        try {
            // Parse user credentials from the request
            User credentials = new ObjectMapper().readValue(request.getInputStream(), User.class);

            // Create an authentication token
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    credentials.getUsername(),
                    credentials.getPassword()
            );

            // Authenticate user
            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            return authentication;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult
    ) throws IOException, ServletException {
        // Get user details from the authenticated result
        org.springframework.security.core.userdetails.User userDetails =
                (org.springframework.security.core.userdetails.User) authResult.getPrincipal();

        // Create JWT token
        String token = Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtConfig.getExpiration()))
                .signWith(SignatureAlgorithm.HS512, jwtConfig.getSecret())
                .compact();

        // Add token to the response header
        response.addHeader(jwtConfig.getHeader(), jwtConfig.getPrefix() + token);
    }
}
