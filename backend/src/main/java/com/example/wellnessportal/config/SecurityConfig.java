package com.example.wellnessportal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * Configures the application's web security.
 * This class enables Spring Security and defines the rules for CORS, CSRF, and
 * request authorization.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Defines the main security filter chain for the application.
     * This bean configures how HTTP requests are handled by Spring Security.
     *
     * @param http The HttpSecurity object to be configured.
     * @return The configured SecurityFilterChain.
     * @throws Exception if an error occurs during configuration.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Enable Cross-Origin Resource Sharing (CORS) using the corsConfigurationSource
                // bean.
                // withDefaults() applies the CORS configuration defined elsewhere in this
                // class.
                .cors(withDefaults())
                // Disable Cross-Site Request Forgery (CSRF) protection.
                // This is common for stateless REST APIs that use tokens for authentication
                // instead of cookies.
                .csrf(csrf -> csrf.disable())
                // Configure authorization rules for HTTP requests.
                .authorizeHttpRequests(authz -> authz
                        // For now, permit all requests without authentication.
                        // This is useful during development but should be secured before production.
                        // TODO: Replace .permitAll() with specific authorization rules (e.g.,
                        // .authenticated(), .hasRole("ADMIN")).
                        .anyRequest().permitAll());
        return http.build();
    }

    /**
     * Defines the Cross-Origin Resource Sharing (CORS) configuration.
     * This bean allows the frontend application (running on a different origin) to
     * communicate with the backend.
     *
     * @return A CorsConfigurationSource with the defined CORS rules.
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // Specify the list of allowed origins.
        // This should include the URLs of your frontend applications.
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200", "http://localhost:3000"));
        // Specify the list of allowed HTTP methods (e.g., GET, POST, etc.).
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        // Specify the list of allowed HTTP headers.
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // Apply this CORS configuration to all paths in the application.
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}