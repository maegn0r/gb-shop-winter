package ru.gb.gbexternalapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class GbExternalApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(GbExternalApiApplication.class, args);
    }

}
