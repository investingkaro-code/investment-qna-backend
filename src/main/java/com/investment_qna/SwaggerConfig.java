package com.investment_qna;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Investment QnA API")
                .version("1.0")
                .description("API documentation for the Investment QnA platform")
                .contact(new Contact()
                    .name("Your Name")
                    .email("your.email@example.com")
                )
            );
    }
}
