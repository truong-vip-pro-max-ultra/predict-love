package com.truongjae.predictlove.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer(){
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins(HttpMethod.OPTIONS.name(),
                                HttpMethod.DELETE.name(),
                                HttpMethod.PUT.name(),
                                HttpMethod.PATCH.name(),
                                HttpMethod.GET.name(),
                                HttpMethod.POST.name())
                        .maxAge(31536000)
                        .allowCredentials(false)
                        .allowedOrigins("*")
                        .allowedHeaders("Content-Type","X-AUTH-TOKEN","Cache-Control","Origin","Authorization");
            }
        };
    }
}
