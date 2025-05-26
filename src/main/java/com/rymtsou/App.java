package com.rymtsou;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@OpenAPIDefinition(
        info = @Info(
                title = "Blog web app",
                description = "Blog web application on Spring Data.",
                contact = @Contact(
                        name = "Illia Rymtsou",
                        email = "rimtsov.ilya@gmail.com",
                        url = "https://github.com/sosopyl61/blog-web-application"
                )
        )
)
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
