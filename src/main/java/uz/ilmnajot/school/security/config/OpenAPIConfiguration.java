package uz.ilmnajot.school.security.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "Education System API",
                version = "1.0",
                description = "This API exposes endpoints to manage Education System.",
                contact = @Contact(name = "SamPM", email = "user@gmail.com")
        ),
        servers = {
                @Server(url = "http://localhost:8080", description = "Education server"),
        }
)
@SecurityScheme(
        name = "Bearer",
        description = "JWT Bearer token",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
@Configuration
public class OpenAPIConfiguration {
//
//    @Bean
//    public OpenAPI customOpenAPI() {
//        return new OpenAPI()
//                .addSecurityItem(new SecurityRequirement().addList("Bearer"))
//                .components(new io.swagger.v3.oas.models.Components()
//                        .addSecuritySchemes("Bearer",
//                                new io.swagger.v3.oas.models.security.SecurityScheme()
//                                        .type(SecurityScheme.Type.HTTP)
//                                        .scheme("bearer")
//                                        .bearerFormat("JWT")));
//
//    }
}