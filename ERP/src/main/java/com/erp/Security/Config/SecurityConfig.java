package com.erp.Security.Config;

import com.erp.Config.AppEnv;
import com.erp.Security.Filter.AuthFilter;
import com.erp.Security.Filter.RefreshAuthFilter;
import com.erp.Security.JWT.JWTService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@AllArgsConstructor
public class SecurityConfig {

    private final AppEnv env;
    private final JWTService jwtService;

    @Bean
    PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManger(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    @Order(3)
    SecurityFilterChain publicSecurityFilterChain(HttpSecurity http) throws Exception {
        String baseUrl = env.getBaseUrl();
        return http.csrf(csrf -> csrf.disable())
                .securityMatchers(match ->
                        match.requestMatchers(baseUrl + "/auth/register/**", baseUrl + "/login" ))//basically used to configure filter chain to accept request made to a specific pattern default ="/**"
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(baseUrl + "/auth/register/**", baseUrl + "/login")
                        // .requestMatchers(baseUrl+"/restaurant_register/{userId}").hasAuthority("ADMIN")
                        .permitAll()
                        .anyRequest()
                        .authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // .formLogin(Customizer.withDefaults())
                .addFilterBefore(new AuthFilter(jwtService), UsernamePasswordAuthenticationFilter.class)
                .build();

    }

    @Bean
    @Order(2)
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        String baseUrl = env.getBaseUrl();

        return http.csrf(csrf -> csrf.disable())
                .securityMatchers(match ->
                        match.requestMatchers( baseUrl + "/root/**" ))//basically used to configure filter chain to accept request made to a specific pattern default ="/**"
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(baseUrl + "/root/**").hasAnyAuthority("ROOT")
                        // .requestMatchers(baseUrl+"/restaurant_register/{userId}").hasAuthority("ADMIN")
                        .anyRequest()
                        .authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // .formLogin(Customizer.withDefaults())
                .addFilterBefore(new AuthFilter(jwtService), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    @Order(1)
    SecurityFilterChain refreshSecurityFilterChain(HttpSecurity http) throws Exception {
        String baseUrl = env.getBaseUrl();

        return http.csrf(csrf -> csrf.disable())
                .securityMatchers(match ->
                        match.requestMatchers(baseUrl + "/refresh-login/**"))
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest()
                        .authenticated())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(new RefreshAuthFilter(jwtService), UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
