package com.hongfei.springbootrabbitmqconsumer.rabbitmq.topic;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author hongfei.shen
 * @date 2019/07/01
 */
@Component
@RabbitListener(queues = "q_topic_messages")
public class TopicReceiver2 {

    @RabbitHandler
    public void process(String hello) {
        System.out.println("topic_Receiver2  : " + hello);
    }
}
