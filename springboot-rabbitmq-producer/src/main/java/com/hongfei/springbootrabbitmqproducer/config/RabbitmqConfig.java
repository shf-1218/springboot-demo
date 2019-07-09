package com.hongfei.springbootrabbitmqproducer.config;


import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author hongfei.shen
 * @date 2019/06/28
 */
@Configuration
public class RabbitmqConfig {

    @Bean
    public Queue queueHello() {
        return new Queue("q_hello", true, false, true);
    }

    //  声明队列【参数说明：参数一：队列名称，参数二：是否持久化；参数三：是否独占模式；参数四：消费者断开连接时是否删除队列；参数五：消息其他参数】
    @Bean
    public Queue queueWork() {
        return new Queue("q_work", true, false, true);
    }

}
