package com.hongfei.springbootapplication.controller;

import com.hongfei.springbootapplication.config.MailConfig;
import com.hongfei.springbootapplication.config.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @program: springboot-demo
 * @Date: 2019-04-18 23:18
 * @Author: Mr.Shen
 * @Description: 获取配置文件内容
 */
@RestController
@RequestMapping("properties")
public class PropertiesController {

    private static Logger logger = LoggerFactory.getLogger(PropertiesController.class);

    @Resource
    private Person person;

    @Resource
    private MailConfig mailConfig;

    @RequestMapping("getPerson")
    public Person getPerson() {
        return person;
    }

    @RequestMapping("getMailConfig")
    public MailConfig getMailConfig() {
        return mailConfig;
    }
}
