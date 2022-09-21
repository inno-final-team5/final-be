package com.sparta.innovationfinal;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@EnableBatchProcessing
@SpringBootApplication
public class InnovationFinalApplication {

    public static void main(String[] args) {
        SpringApplication.run(InnovationFinalApplication.class, args);
    }

}
