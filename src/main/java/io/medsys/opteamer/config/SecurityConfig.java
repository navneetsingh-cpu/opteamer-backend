package io.medsys.opteamer.config;

// Importing necessary classes for security configuration

import io.medsys.opteamer.config.filters.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

/**
 * Security configuration for the application.
 * Configures authentication, authorization, and other security features.
 */
@Configuration // Marks this class as a configuration class for Spring
@EnableWebSecurity // Enables Spring Security for the application
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true) // Enables method-level security
public class SecurityConfig {

    @Autowired
    @Qualifier("OpteamerUserDetailsService")
    private UserDetailsService userDetailsService; // Service to load user details for authentication

    @Autowired
    private JwtRequestFilter jwtRequestFilter; // Custom filter to process JWT tokens

    /**
     * Configures the AuthenticationManager with a custom UserDetailsService and password encoder.
     */
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        // Configure AuthenticationManager to use the custom UserDetailsService and BCryptPasswordEncoder
        authenticationManagerBuilder.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }

    /**
     * Provides a BCryptPasswordEncoder bean to securely encode passwords.
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Uses BCrypt hashing for encoding passwords
    }

    /**
     * Configures the security filter chain.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Enable Cross-Origin Resource Sharing (CORS) with default configuration
                .cors(Customizer.withDefaults())
                // Disable Cross-Site Request Forgery (CSRF) protection as JWT is used
                .csrf(AbstractHttpConfigurer::disable)
                // Configure authorization rules for specific endpoints
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/api/userregistration").permitAll() // Allow unauthenticated access to user registration API
                        .requestMatchers("/api/authenticate").permitAll() // Allow unauthenticated access to authentication API
                        .anyRequest().authenticated() // Require authentication for all other requests
                )
                // Add the custom JWT filter before Spring's built-in UsernamePasswordAuthenticationFilter
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                // Enable logout functionality and permit all users to log out
                .logout((logout) -> logout.permitAll());
        return http.build(); // Build and return the security filter chain
    }

    /**
     * Configures the CORS settings for the application.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // Allow all origins (not recommended for production)
        configuration.setAllowedOrigins(Arrays.asList("*"));
        // Allow HTTP methods commonly used in RESTful APIs
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        // Allow specific HTTP headers in requests
        configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
        // Create a source for URL-based CORS configuration and register it for all paths
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
