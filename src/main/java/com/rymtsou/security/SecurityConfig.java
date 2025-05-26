package com.rymtsou.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
                                .requestMatchers(HttpMethod.GET, "/actuator/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/posts/**").hasAnyRole("USER", "ADMIN")
                                .requestMatchers(HttpMethod.POST, "/likes/**").hasAnyRole("USER", "ADMIN")
                                .requestMatchers(HttpMethod.GET, "/users/find").hasAnyRole("USER", "ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/comments").hasAnyRole("USER", "ADMIN")
                                .requestMatchers(HttpMethod.POST, "/registration").permitAll()
                                .requestMatchers(HttpMethod.POST, "/login").permitAll()
                                .requestMatchers(HttpMethod.POST, "/posts").hasAnyRole("USER", "ADMIN")
                                .requestMatchers(HttpMethod.POST, "/comments").hasAnyRole("USER", "ADMIN")
                                .requestMatchers(HttpMethod.GET, "/users/{id}").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/users/find/all").hasAnyRole("USER", "ADMIN")
                                .requestMatchers(HttpMethod.GET, "/security/**").hasAnyRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/comments/**").hasAnyRole("USER", "ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/users").hasAnyRole("USER", "ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/security").hasAnyRole("USER", "ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/posts").hasAnyRole("USER", "ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/comments").hasAnyRole("USER", "ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/users").hasAnyRole("USER", "ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/posts").hasAnyRole("USER", "ADMIN")
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
