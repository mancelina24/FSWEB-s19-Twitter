package com.workintech.twitter.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class CorsConfig  implements WebMvcConfigurer {

 /*@Value("${cors.allowed-origins}") // application.properties'den okunacak
    private String allowedOrigins;*/

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Tüm endpoint'lere CORS uygulanacak
                .allowedOrigins("http://localhost:5173") // Sadece bu domain'e izin ver // React portu
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // İzin verilen HTTP metodları
                .allowedHeaders("*") // Tüm header'lara izin ver
                .allowCredentials(true) // Cookie ve Authorization header'a izin ver
                .maxAge(3600); // OPTIONS isteği önbellekte 1 saat tutulur
    }
}
