package com.teste.coupon_service.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI api() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title("Coupon Service API")
                        .description("API para criação e deleção de cupons conforme desafio técnico")
                        .version("v1"))
                .externalDocs(new ExternalDocumentation()
                        .description("Desafio Técnico")
                        .url("https://n1m0i5k0zu.apidog.io/"));
    }
}
