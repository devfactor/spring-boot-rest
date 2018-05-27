package com.devfactor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * Spring Boot application startup. When refactoring or moving classes around, remember
 * to keep this at the top of all packages, as Spring expects that.
 * Otherwise, all hell will break loose, and nothing will work.
 *
 * Some documentation:
 * - https://docs.spring.io/spring-boot/docs/current-SNAPSHOT/reference/htmlsingle/#production-ready-endpoints
 * - http://www.baeldung.com/spring-boot-actuators
 * - Swagger: http://www.baeldung.com/swagger-2-documentation-for-spring-rest-api
 * - Swagger: http://localhost:8080/swagger-ui.html
 *
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        // Do any additional configuration here
        return builder.build();
    }

}
