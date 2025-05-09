package com.example.clubmgmt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        
        //Permitir todas las credenciales
        config.setAllowCredentials(true);
        
        //Permitir todos los orígenes (para desarrollo)
        config.addAllowedOriginPattern("*");
        
        //Permitir todos los encabezados incluyendo Authorization
        config.addAllowedHeader("*");
        
        //Permitir todos los métodos HTTP
        config.addAllowedMethod("*");
        
        //Exponer el header Authorization en la respuesta
        config.addExposedHeader("Authorization");
        
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}