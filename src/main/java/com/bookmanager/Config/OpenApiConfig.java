package com.bookmanager.Config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@io.swagger.v3.oas.annotations.OpenAPIDefinition(
        info = @io.swagger.v3.oas.annotations.info.Info(
                title = "Book Manager API",
                version = "1.0.0",
                description = "API quản lý thư viện sách",
                contact = @io.swagger.v3.oas.annotations.info.Contact(
                        name = "Support Team",
                        email = "support@bookmanager.com"
                ),
                license = @io.swagger.v3.oas.annotations.info.License(
                        name = "Apache 2.0",
                        url = "https://www.apache.org/licenses/LICENSE-2.0.html"
                )
        ),
        servers = {
                @io.swagger.v3.oas.annotations.servers.Server(
                        description = "Local Server",
                        url = "http://localhost:8080"
                )
        }
)
@io.swagger.v3.oas.annotations.security.SecurityScheme(
        name = "bearerAuth",
        description = "JWT authentication - Nhập token sau khi login",
        scheme = "bearer",
        type = io.swagger.v3.oas.annotations.enums.SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = io.swagger.v3.oas.annotations.enums.SecuritySchemeIn.HEADER
)
public class OpenApiConfig {

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
                .info(new Info()
                        .title("My API")
                        .description("API Documentation")
                        .version("1.0"));
    }

    @Bean
    public OpenApiCustomizer hideDefaultResponses() {
        return openApi -> openApi.getPaths().values().forEach(pathItem ->
                pathItem.readOperations().forEach(operation -> {
                    // Xóa toàn bộ responses mẫu
                    operation.getResponses().clear();
                })
        );
    }

}
