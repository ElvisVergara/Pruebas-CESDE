package com.crudac.container.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

    @Configuration
    public class OpenApiConfig {

        @Bean
        public OpenAPI customOpenAPI() {
            return new OpenAPI()
                    .info(new Info()
                            .title("API AQUICREAMOS")
                            .version("1.0.0")
                            .description("Documentación de la API para mi aplicación Spring Boot.")
                            .termsOfService("http://tu-dominio.com/terminos-de-servicio")
                            .contact(new Contact()
                                    .name("Tu AQUICREAMOS")
                                    .url("http://aquicreamos.com.com")
                                    .email("contacto@aquicreamos.com"))
                            .license(new License()
                                    .name("Apache 2.0")
                                    .url("http://www.apache.org/licenses/LICENSE-2.0.html")));
        }
    }