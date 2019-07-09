package com.hongfei.springbootrabbitmqconsumer.rabbitmq.fanout;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author hongfei.shen
 * @date 2019/07/01
 */
@Component
@RabbitListener(queues = "q_fanout_A")
public class FanoutReceiverA {

    @RabbitHandler
    public void process(String hello) {
        System.out.println("FanoutReceiverA  : " + hello + "/n");
    }
}
