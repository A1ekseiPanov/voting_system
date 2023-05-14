package ru.panov.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
        name = "basicAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "basic"
)
@OpenAPIDefinition(
        info = @Info(
                title = "Voting System Api",
                description = """
                        Restaurant Voting System
                        <p><b>Тестовые креденшелы:</b><br>
                        - user: user@yan.ru / 12345<br>
                        - admin: user@ya.ru / 54321</p>
                        """,
                version = "1.0.0",
                contact = @Contact(
                        name = "Aleksey Panov",
                        email = "evil199315@yandex.ru"
                )
        ),
        security = @SecurityRequirement(name = "basicAuth")
)
public class OpenApiConfig {

}
