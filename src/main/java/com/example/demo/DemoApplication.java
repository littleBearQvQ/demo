package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DemoApplication {

    public static void main(String[] args) {

        /*SpringApplication.run(DemoApplication.class, args);*/
        SpringApplicationBuilder builder = new SpringApplicationBuilder(DemoApplication.class);
        builder.headless(false).run(args);
    }

}
