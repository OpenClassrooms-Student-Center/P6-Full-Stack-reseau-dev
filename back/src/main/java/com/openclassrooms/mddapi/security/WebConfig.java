package com.openclassrooms.mddapi.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${server.hostname}")
    private String hostname;

    @Value("${server.port}")
    private int port;
    @Value("${server.protocol}")
    private String protocol;




    public String getHostname() {
        return hostname;
    }

    public int getPort() {
        return port;
    }

    public String getProtocol() {
        return protocol;
    }



 /*   @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:4200/**")  // Autoriser votre front-end
                .allowedMethods("GET", "POST", "PUT")
                .allowedHeaders("*")
                .allowCredentials(true);
    }*/
}
