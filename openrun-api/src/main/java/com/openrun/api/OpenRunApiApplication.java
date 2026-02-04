package com.openrun.api;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.openrun.core.mapper")
@ComponentScan(basePackages = "com.openrun")
public class OpenRunApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(OpenRunApiApplication.class, args);
    }

}
