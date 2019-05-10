package com.hongfei.springbootapplication;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

/**
 * springboot 启动类
 */
@SpringBootApplication
public class SpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext
                                                       applicationContext) {
        return args -> {
            System.out.println("Let's inspect the beans provided by Spring Boot:");

            String[] beanNames = applicationContext.getBeanDefinitionNames();

            Arrays.sort(beanNames);

            Arrays.asList(beanNames).stream().forEach(e -> {
                System.out.println(e);
            });
        };

    }

}
