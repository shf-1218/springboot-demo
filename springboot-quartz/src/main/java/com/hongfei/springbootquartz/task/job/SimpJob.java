package com.hongfei.springbootquartz.task.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author hongfei.shen
 * @date 2019/06/21
 */
@Slf4j
public class SimpJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("+++++++定时任务2+++++++");
    }
}
