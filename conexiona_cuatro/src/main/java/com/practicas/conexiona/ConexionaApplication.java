package com.practicas.conexiona;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@EnableJpaRepositories("com.practicas.conexiona.repo")
@EntityScan("com.practicas.conexiona.model")

@Configuration
public class ConexionaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConexionaApplication.class, args);
    }

}
