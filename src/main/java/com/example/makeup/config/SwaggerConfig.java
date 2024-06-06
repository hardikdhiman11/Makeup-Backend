package com.example.makeup.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    public Info setInfo(){
        Info info = new Info();
        info.setVersion("1.0.0");
        info.setContact(new Contact().email("hardik11dhiman@gmail.com"));
        info.setDescription("Backend Apis for makeup project.");
        info.setTitle("Makeup Project");
        return info;
    }

    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI()
                .info(setInfo())
                .addSecurityItem(new SecurityRequirement().addList("Bearer Token Authentication"))
                .components(new Components()
                        .addSecuritySchemes("JWT",
                                            new SecurityScheme()
                                            .bearerFormat("BEARER")
                                            .name("Authorization")));
    }
}
