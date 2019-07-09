package com.hongfei.springbootrabbitmqproducer.rabbitmq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author hongfei.shen
 * @date 2019/07/01
 */
@Component
public class WorkSender {
    @Autowired
    private AmqpTemplate amqpTemplate;

    public void send(int i) {
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());//24小时制
        String context = "hello " + i + " " + date;
        //简单对列的情况下routingKey即为Q名
        this.amqpTemplate.convertAndSend("q_work", context);
    }

}
