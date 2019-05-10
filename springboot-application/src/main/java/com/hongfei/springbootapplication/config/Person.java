package com.hongfei.springbootapplication.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @program: springboot-demo
 * @Date: 2019-04-18 23:15
 * @Author: Mr.Shen
 * @Description: 配置文件
 */
@Component
@ConfigurationProperties(prefix = "person")
public class Person {
    private int age;
    private String name;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
