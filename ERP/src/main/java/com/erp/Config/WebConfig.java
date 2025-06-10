package com.erp.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // applies to all paths
                        .allowedOrigins("http://localhost:5173")
                        .allowedOriginPatterns("https://.*\\.ngrok-free\\.app") // dynamic ngrok subdomains
                        .allowedMethods("*")  // GET, POST, etc.
                        .allowedHeaders("*") // all headers
                        .allowCredentials(true);
            }
        };
    }
}