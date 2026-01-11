package com.pixiehex.kshipping;

import com.pixiehex.kshipping.security.ClientDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {

    private final ClientDetailsService clientDetailsService;

    public SecurityConfig(ClientDetailsService clientDetailsService) {
        this.clientDetailsService = clientDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // ✅ Enable CORS for React / ngrok
                .cors(Customizer.withDefaults())

                // ✅ Disable CSRF for REST API
                .csrf(csrf -> csrf.disable())

                // ✅ Everyone can access everything
                .authorizeHttpRequests(auth -> auth

                        /* =========================
                           AUTH (PUBLIC)
                           ========================= */
                        .requestMatchers("/auth/**").permitAll()

                        /* =========================
                           PRODUCTS
                           ========================= */
                        .requestMatchers(HttpMethod.GET, "/products/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/products/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/products/**").hasRole("ADMIN")

                        /* =========================
                           PHOTOS
                           ========================= */
                        .requestMatchers(HttpMethod.GET, "/photos/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/photos/**").hasRole("ADMIN")

                        /* =========================
                           SINGLE ORDERS
                           ========================= */
                        .requestMatchers(HttpMethod.GET, "/single_orders/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/single_orders/**").permitAll()   // users create orders
                        .requestMatchers(HttpMethod.PUT, "/single_orders/**").permitAll()    // pay
                        .requestMatchers(HttpMethod.PATCH, "/single_orders/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/single_orders/**").hasRole("ADMIN")

                        /* =========================
                           GROUP ORDERS
                           ========================= */
                        .requestMatchers(HttpMethod.GET, "/group_order/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/group_order/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/group_order/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/group_order/**").hasRole("ADMIN")

                        /* =========================
                           BATCH (ADMIN ONLY)
                           ========================= */
                        .requestMatchers("/batch/**").hasRole("ADMIN")

                        /* =========================
                           CLIENTS (ADMIN ONLY)
                           ========================= */
                        .requestMatchers("/client/**").hasRole("ADMIN")

                        /* =========================
                           REPORTS (ADMIN ONLY)
                           ========================= */
                        .requestMatchers("/reports/**").hasRole("ADMIN")

                        /* =========================
                           CORS PREFLIGHT
                           ========================= */
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        /* =========================
                           DEFAULT
                           ========================= */
                        .anyRequest().denyAll()
                )


                // ✅ Required for H2 console
                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()))

                // ❌ Disable default login page
                .formLogin(form -> form.disable())

                .logout(logout -> logout.permitAll());

        return http.build();
    }

    // ✅ Password encoder for register/login
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // ✅ Needed for AuthenticationManager in controller
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // ✅ CORS CONFIG — THIS FIXES FETCH()
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOrigins(List.of(
                "http://localhost:3000",
                "http://localhost:5173",
                "https://unexchangeable-julio-acaroid.ngrok-free.dev"
        ));

        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
