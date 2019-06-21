package com.hongfei.springbootquartz.task.controller;

import com.hongfei.springbootquartz.common.model.Result;
import com.hongfei.springbootquartz.task.model.JobInfo;
import com.hongfei.springbootquartz.task.model.dto.ModifyCronDTO;
import com.hongfei.springbootquartz.task.service.JobInfoService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author hongfei.shen
 * @date 2019/06/20
 */
@Slf4j
@RestController
@RequestMapping("job")
public class JobInfoController {

    @Autowired
    private JobInfoService jobInfoService;


    //初始化启动所有的Job
    @PostConstruct
    public void initialize() {
        try {
            jobInfoService.reStartAllJobs();
            log.info("INIT SUCCESS");
        } catch (Exception e) {
            log.error("printStackTrace ", e);
        }
    }

    @GetMapping("list")
    public List<JobInfo> list() {
        List<JobInfo> jobInfoList = this.jobInfoService.loadJobs();
        return jobInfoList;
    }

    //根据ID重启某个Job
    @GetMapping("refresh/{id}")
    public Result refresh(@PathVariable @NotNull Long id) throws IllegalAccessException, InstantiationException,
            ClassNotFoundException, SchedulerException {
        Result result = jobInfoService.refresh(id);
        return result;
    }


    //重启数据库中所有的Job
    @GetMapping("refresh/all")
    public Result refreshAll() {
        try {
            this.jobInfoService.reStartAllJobs();
        } catch (Exception e) {
            log.error("EXCEPTION : " + e.getMessage());
            Result.error("EXCEPTION : " + e.getMessage());
        }
        return Result.success("refresh all jobs");
    }


    //修改某个Job执行的Cron
    @PostMapping("modifyJob")
    public Result modifyJob(@RequestBody @Validated ModifyCronDTO dto) {
        Result result = this.jobInfoService.modifyJob(dto);
        return result;
    }

    //删除任务
    @DeleteMapping("delete/{id}")
    public Result delete(@PathVariable @NotNull Long id) {
        Result result = this.jobInfoService.delete(id);
        return result;
    }

    //暂停某个任务
    @GetMapping("suspend/{id}")
    public Result suspend(@PathVariable @NotNull Long id) {
        Result result = this.jobInfoService.suspend(id);
        return result;
    }

    //暂停全部任务
    @GetMapping("suspend/all")
    public Result suspend() {
        Result result = this.jobInfoService.suspendAll();
        return result;
    }
}
