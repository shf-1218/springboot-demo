package com.hongfei.springbootjedis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = "com.hongfei.springbootjedis.mapper")
@EnableCaching
public class SpringbootJedisApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootJedisApplication.class, args);
    }

}
