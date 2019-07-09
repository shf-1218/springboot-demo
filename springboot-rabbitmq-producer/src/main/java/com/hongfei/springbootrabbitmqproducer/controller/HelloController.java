package com.hongfei.springbootrabbitmqproducer.controller;

import com.hongfei.springbootrabbitmqproducer.rabbitmq.FanoutSender;
import com.hongfei.springbootrabbitmqproducer.rabbitmq.HelloSender;
import com.hongfei.springbootrabbitmqproducer.rabbitmq.TopicSender;
import com.hongfei.springbootrabbitmqproducer.rabbitmq.WorkSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author hongfei.shen
 * @date 2019/06/28
 */
@RestController
public class HelloController {
    @Resource
    private HelloSender helloSender;
    @Resource
    private WorkSender workSender;
    @Resource
    private TopicSender topicSender;
    @Resource
    private FanoutSender fanoutSender;

    @GetMapping("hello")
    public void hello() {
        this.helloSender.send();
    }

    @GetMapping("work")
    public void work() throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            this.workSender.send(i);
            Thread.sleep(300);
        }

    }

    @GetMapping("topic1")
    public void topic1() {
        this.topicSender.send1();

    }

    @GetMapping("topic2")
    public void topic2() {
        this.topicSender.send2();
    }

    @GetMapping("fanout")
    public void send1() {
        fanoutSender.send();
    }
}
