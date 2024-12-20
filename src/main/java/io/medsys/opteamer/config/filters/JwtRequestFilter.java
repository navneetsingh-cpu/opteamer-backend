package io.medsys.opteamer.config.filters;

// Importing necessary classes for the filter

import io.medsys.opteamer.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// Marking this class as a Spring-managed component
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    // UserDetailsService provides user information for authentication
    private final UserDetailsService userDetailsService;

    // JwtUtil contains utility methods for JWT (e.g., extracting username, validating token)
    private final JwtUtil jwtUtil;

    // Constructor to inject dependencies using Spring's dependency injection
    public JwtRequestFilter(@Qualifier("OpteamerUserDetailsService") UserDetailsService userDetailsService, JwtUtil jwtUtil) {
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    // This method filters each HTTP request to validate JWT tokens
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            // Get the Authorization header from the HTTP request
            final String authorizationHeader = httpServletRequest.getHeader("Authorization");

            String username = null; // To store the extracted username from the JWT
            String jwt = null;      // To store the JWT itself

            // Check if the Authorization header contains a Bearer token
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                // Extract the JWT from the header by removing the "Bearer " prefix
                jwt = authorizationHeader.substring(7);
                // Use the JwtUtil to extract the username from the token
                username = jwtUtil.extractUsername(jwt);
            }

            // If a username is extracted and no authentication is set in the SecurityContext
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // Load user details from the UserDetailsService
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                // Validate the JWT with the user details
                if (jwtUtil.validateToken(jwt, userDetails)) {
                    // Create an authentication token for the user
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    // Set additional details from the HTTP request
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                    // Set the authentication in the SecurityContext
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
        } catch (Exception e) {
            // Catch and suppress any exceptions during the filter process (could be improved by logging the error)
        }

        // Continue the filter chain to process the next filters
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
