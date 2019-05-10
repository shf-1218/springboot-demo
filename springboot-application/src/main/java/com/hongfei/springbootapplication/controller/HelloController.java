package com.hongfei.springbootapplication.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: springboot-demo
 * @Date: 2019-04-18 22:49
 * @Author: Mr.Shen
 * @Description: Hello world
 */
@RestController
public class HelloController {

    @RequestMapping("/")
    public String hello() {
        return "Hello World";
    }
}
