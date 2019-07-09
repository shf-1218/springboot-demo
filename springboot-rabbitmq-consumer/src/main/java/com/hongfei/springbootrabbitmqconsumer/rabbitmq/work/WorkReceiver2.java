package com.hongfei.springbootrabbitmqconsumer.rabbitmq.work;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author hongfei.shen
 * @date 2019/07/01
 */
@Component
@RabbitListener(queues = "q_work")
public class WorkReceiver2 {

    @RabbitHandler
    public void process(String hello) {
        System.out.println("Receiver2  : " + hello);
    }
}
