package com.tifac;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableFeignClients
@EnableCaching
@EnableAsync
@OpenAPIDefinition(info = @Info(title = "Tifac: Youtube AKGEC Educational Video Platform",version = "1.0",description = "This compromises complete backend for the proper working of TIFAC Video Streaming application, this application fetches the data from the youtube and stores the data to the database in-order to reduce the cost of youtube API usage(Daily Limit)"))
@EnableScheduling
public class TifacBackendApplication {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        return modelMapper;
    }

    public static void main(String[] args) {
        SpringApplication.run(TifacBackendApplication.class, args);
    }

}
