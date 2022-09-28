package com.sparta.innovationfinal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableJpaAuditing
@EnableSwagger2
public class InnovationFinalApplication {

    public static void main(String[] args) {
        SpringApplication.run(InnovationFinalApplication.class, args);
    }

}
