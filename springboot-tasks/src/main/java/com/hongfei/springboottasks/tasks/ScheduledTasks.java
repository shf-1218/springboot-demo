package com.hongfei.springboottasks.tasks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @program: springboot-demo
 * @Date: 2019-04-21 19:15
 * @Author: Mr.Shen
 * @Description: 创建定时任务
 */
@Component
public class ScheduledTasks {

    private static  Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);

    @Async
    @Scheduled(cron = "0/1 * * * * *")
    public void scheduled1() throws InterruptedException {
        Thread.sleep(3000);
        logger.info("scheduled1 每1秒执行一次：{}", LocalDateTime.now());
    }
    @Async
    @Scheduled(fixedRate = 1000)
    public void scheduled2() throws InterruptedException {
        Thread.sleep(3000);
        logger.info("scheduled2 每1秒执行一次：{}", LocalDateTime.now());
    }
    @Async
    @Scheduled(fixedDelay = 3000)
    public void scheduled3() throws InterruptedException {
        Thread.sleep(5000);
        logger.info("scheduled3 上次执行完毕后隔3秒继续执行：{}", LocalDateTime.now());
    }
}
