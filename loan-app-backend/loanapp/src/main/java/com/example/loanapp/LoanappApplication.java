package com.example.loanapp;

import com.example.loanapp.model.Customer;
import com.example.loanapp.repository.CustomerRepository;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.properties.SwaggerUiConfigParameters;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication
public class LoanappApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoanappApplication.class, args);
    }

    @Bean
    public OpenAPI customOpenAPI (@Value("${application-description}") String description,
                                  @Value("${application-version}") String version){
        return new OpenAPI()
                .info(new Info()
                        .title("Loan Application System API")
                        .version(version)
                        .description(description)
                        .license(new License().name("Furkan Balikci API Licence")));
    }




}
