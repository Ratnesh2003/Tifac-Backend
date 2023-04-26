package com.example.tifac_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class TifacBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(TifacBackendApplication.class, args);
    }

}
