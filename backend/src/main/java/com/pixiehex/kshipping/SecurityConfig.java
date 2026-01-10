package com.pixiehex.kshipping.config;

import com.pixiehex.kshipping.security.ClientDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final ClientDetailsService clientDetailsService;

    public SecurityConfig(ClientDetailsService clientDetailsService) {
        this.clientDetailsService = clientDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/register", "/auth/login", "/h2-console/**").permitAll() // Allow these without auth
                        .anyRequest().permitAll()
                )
                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()))
                .formLogin(form -> form.disable())
                .logout(logout -> logout.permitAll());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Expose AuthenticationManager so you can authenticate programmatically in your controller
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
