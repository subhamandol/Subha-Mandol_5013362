package com.library.bookstore.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtFilter extends UsernamePasswordAuthenticationFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            String username = jwtUtil.extractUsername(token);
            if (username != null && jwtUtil.validateToken(token, username)) {
                SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(username, null, null));
            }
        }
        chain.doFilter(request, response);
    }
}
