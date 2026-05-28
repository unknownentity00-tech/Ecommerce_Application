package com.EcomFashion.Ecommorse.Auth.Config;


import com.EcomFashion.Ecommorse.Auth.Service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final UserDetailsService userDetailsService;


    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        try {
            final String authHeader = request.getHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                filterChain.doFilter(
                        request,
                        response);
                return;
            }
            final String jwtToken = authHeader.substring(7);
            if (!jwtService.isTokenValid(jwtToken)) {
                log.warn("Invalid JWT token received");
                filterChain.doFilter(
                        request,
                        response);
                return;
            }
            jwtService.validateAccessToken(
                    jwtToken
            );

            final String userEmail = jwtService.extractEmail(jwtToken);

            if (userEmail != null
                    && SecurityContextHolder
                    .getContext()
                    .getAuthentication() == null) {
                UserDetails userDetails =
                        userDetailsService
                                .loadUserByUsername(
                                        userEmail
                                );
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken
                                (userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(
                        new WebAuthenticationDetailsSource()
                                .buildDetails(request)
                );
                SecurityContextHolder
                        .getContext()
                        .setAuthentication(authToken);
                log.info(
                        "User authenticated successfully: {}",
                        userEmail
                );
            }
            filterChain.doFilter(
                    request,
                    response
            );

        } catch (Exception ex) {
            log.error("JWT Authentication Error: {}",
                    ex.getMessage());
            SecurityContextHolder.clearContext();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType(
                    "application/json"
            );
            response.getWriter().write(
                    """
                            {
                                "error": "Unauthorized",
                                "message": "Invalid or expired token"
                            }
                            """
            );
        }
    }
}