package com.baozi.easyps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class EasypsApplication {

    public static void main(String[] args) {
        SpringApplication.run(EasypsApplication.class, args);
    }

}
