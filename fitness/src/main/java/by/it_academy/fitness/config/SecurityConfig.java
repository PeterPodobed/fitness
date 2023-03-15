package by.it_academy.fitness.config;

import by.it_academy.fitness.web.controllers.filter.JwtFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtFilter filter;

    public SecurityConfig(JwtFilter filter) {
        this.filter = filter;
    }

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        // Enable CORS and disable CSRF
        http = http.cors().and().csrf().disable();

        // Set session management to stateless
        http = http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and();

        // Set unauthorized requests exception handler
        http = http
                .exceptionHandling()
                .authenticationEntryPoint(
                        (request, response, ex) -> {
                            response.sendError(
                                    HttpServletResponse.SC_UNAUTHORIZED,
                                    ex.getMessage()
                            );
                        }
                )
                .and();

        // Set permissions on endpoints
        http.authorizeHttpRequests((requests) -> requests
                .requestMatchers("/**").permitAll()
                .requestMatchers("/api/v1/users").permitAll()
                .requestMatchers("/api/v1/users/registration").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/users/me").authenticated()
                .requestMatchers("/api/v1/users/").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/v1/users/verification").authenticated()
                .requestMatchers("/api/v1/product").hasRole("ADMIN")
                .requestMatchers("/api/v1/product/").hasRole("ADMIN")
                .requestMatchers("/api/v1/recipe").hasRole("ADMIN")
                .requestMatchers("/api/v1/recipe/").hasRole("ADMIN"));


        // Add JWT token filter
        http.addFilterBefore(
                filter,
                UsernamePasswordAuthenticationFilter.class
        );
        return http.build();
    }
}