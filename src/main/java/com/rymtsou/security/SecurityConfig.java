package com.rymtsou.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    @Autowired
    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        requests -> requests
                                .requestMatchers("/security", "PUT").hasAnyRole("USER", "ADMIN")
                                .requestMatchers("/posts", "DELETE").hasAnyRole("USER", "ADMIN")
                                .requestMatchers("/registration", "POST").permitAll()
                                .requestMatchers("/login", "POST").permitAll()
                                .requestMatchers("/users/find/all", "GET").hasAnyRole("USER", "ADMIN")
                                .requestMatchers("/users/find", "GET").hasAnyRole("USER", "ADMIN")
                                .requestMatchers("/users/{id}", "GET").hasRole("ADMIN")
                                .requestMatchers("/users", "PUT").hasAnyRole("USER", "ADMIN")
                                .requestMatchers("/users", "DELETE").hasAnyRole("USER", "ADMIN")
                                .requestMatchers("/security/**", "GET").hasAnyRole("ADMIN")
                                .requestMatchers("/posts", "POST").hasAnyRole("USER", "ADMIN")
                                .requestMatchers("/posts/**", "GET").hasAnyRole("USER", "ADMIN")
                                .requestMatchers("/posts", "PUT").hasAnyRole("USER", "ADMIN")
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
