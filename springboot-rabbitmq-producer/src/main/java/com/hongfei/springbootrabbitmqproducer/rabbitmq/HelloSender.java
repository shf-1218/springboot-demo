package com.hongfei.springbootrabbitmqproducer.rabbitmq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author hongfei.shen
 * @date 2019/06/28
 */
@Component
public class HelloSender {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void send() {
        String message = "Hello " + new Date();
        this.amqpTemplate.convertAndSend(
                "q_hello", message);
    }
}
