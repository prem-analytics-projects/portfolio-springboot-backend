package com.prem.myfirstapp.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:5173")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*");
    }
}


// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.context.annotation.*;
// import org.springframework.web.cors.*;

// import java.util.List;

// @Configuration
// public class CorsConfig {

//     @Bean
//     CorsConfigurationSource corsConfigurationSource(
//             @Value("${app.frontend-url}") String frontendUrl) {
//         CorsConfiguration c = new CorsConfiguration();
//         c.setAllowedOrigins(List.of(frontendUrl));
//         c.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
//         c.setAllowedHeaders(List.of("*"));
//         UrlBasedCorsConfigurationSource src = new UrlBasedCorsConfigurationSource();
//         src.registerCorsConfiguration("/**", c);
//         return src;
//     }
// }