package com.hongfei.springbootquartz.task.service;

import com.hongfei.springbootquartz.common.Constant;
import com.hongfei.springbootquartz.common.model.ResponseCode;
import com.hongfei.springbootquartz.common.model.Result;
import com.hongfei.springbootquartz.task.mapper.JobInfoMapper;
import com.hongfei.springbootquartz.task.model.JobInfo;
import com.hongfei.springbootquartz.task.model.dto.ModifyCronDTO;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import java.lang.invoke.ConstantCallSite;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * @author hongfei.shen
 * @date 2019/06/20
 */
@Slf4j
@Service
public class JobInfoService {
    @Autowired
    private JobInfoMapper jobInfoMapper;
    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;


    //通过Id获取Job
    public JobInfo getById(Long id) {
        return jobInfoMapper.findById(id).get();
    }


    //从数据库中加载获取到所有Job
    public List<JobInfo> loadJobs() {
        List<JobInfo> list = new ArrayList<>();
        jobInfoMapper.findAll().forEach(list::add);
        return list;
    }

    //获取JobDataMap.(Job参数对象)
    public JobDataMap getJobDataMap(JobInfo job) {
        JobDataMap map = new JobDataMap();
        map.put("name", job.getName());
        map.put("jobGroup", job.getJobGroup());
        map.put("cronExpression", job.getCron());
        map.put("jobDescription", job.getDescription());
        map.put("status", job.getStatus());
        return map;
    }

    //获取JobDetail,JobDetail是任务的定义,而Job是任务的执行逻辑,JobDetail里会引用一个Job Class来定义
    public JobDetail getJobDetail(String className, JobKey jobKey, String description, JobDataMap map) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Class cls = Class.forName(className);
        cls.newInstance();
        return JobBuilder.newJob(cls)
                .withIdentity(jobKey)
                .withDescription(description)
                .setJobData(map)
                .storeDurably()
                .build();
    }

    //获取Trigger (Job的触发器,执行规则)
    public Trigger getTrigger(JobInfo job) {
        return TriggerBuilder.newTrigger()
                .withIdentity(job.getName(), job.getJobGroup())
                .withSchedule(CronScheduleBuilder.cronSchedule(job.getCron()))
                .build();
    }

    //获取JobKey,包含Name和Group
    public JobKey getJobKey(JobInfo job) {
        return JobKey.jobKey(job.getName(), job.getJobGroup());
    }


    /**
     * 重新启动所有的job
     */
    public void reStartAllJobs() throws SchedulerException, IllegalAccessException, InstantiationException,
            ClassNotFoundException {
        synchronized (log) {                                                         //只允许一个线程进入操作
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            Set<JobKey> set = scheduler.getJobKeys(GroupMatcher.anyGroup());
            scheduler.pauseJobs(GroupMatcher.anyGroup());                               //暂停所有JOB
            for (JobKey jobKey : set) {                                                 //删除从数据库中注册的所有JOB
                scheduler.unscheduleJob(TriggerKey.triggerKey(jobKey.getName(), jobKey.getGroup()));
                scheduler.deleteJob(jobKey);
            }

            for (JobInfo job : this.loadJobs()) {                               //从数据库中注册的所有JOB
                log.info("Job register name : {} , group : {} , cron : {}", job.getName(), job.getJobGroup(),
                        job.getCron());

                JobDataMap map = this.getJobDataMap(job);
                JobKey jobKey = this.getJobKey(job);
                JobDetail jobDetail = this.getJobDetail(job.getJobClassName(), jobKey, job.getDescription(),
                        map);

                if (Constant.STATUS_OPEN.equals(job.getStatus())) {
                    scheduler.scheduleJob(jobDetail, this.getTrigger(job));
                } else
                    log.info("Job jump name : {} , Because {} status is {}", job.getName(), job.getName(),
                            job.getStatus());
            }
        }
    }

    public Result refresh(Long id) throws IllegalAccessException, InstantiationException, ClassNotFoundException,
            SchedulerException {
        JobInfo jobInfo = this.getById(id);
        if (Objects.isNull(jobInfo)) {
            Result.error(ResponseCode.data_not_exist.getMessage());
        }
        synchronized (log) {
            JobKey jobKey = this.getJobKey(jobInfo);
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            scheduler.pauseJob(jobKey);
            scheduler.unscheduleJob(TriggerKey.triggerKey(jobKey.getName(), jobKey.getGroup()));
            scheduler.deleteJob(jobKey);
            JobDataMap map = this.getJobDataMap(jobInfo);
            JobDetail jobDetail = this.getJobDetail(jobInfo.getJobClassName(), jobKey,
                    jobInfo.getDescription(), map);
            if (Constant.STATUS_OPEN.equals(jobInfo.getStatus())) {
                scheduler.scheduleJob(jobDetail, this.getTrigger(jobInfo));
                return Result.success("Refresh Job : " + jobInfo.getName() + "\t success !");
            } else {
                return Result.error("Refresh Job : " + jobInfo.getName() + "\t failed ! , " +
                        "Because the Job status is " + jobInfo.getStatus());
            }
        }
    }

    public Result modifyJob(ModifyCronDTO dto) {
        if (!CronExpression.isValidExpression(dto.getCron())) {
            return Result.error("cron is invalid !");
        }
        synchronized (log) {
            JobInfo job = this.getById(dto.getId());
            if (Constant.STATUS_OPEN.equals(job.getStatus())) {
                try {
                    JobKey jobKey = this.getJobKey(job);
                    TriggerKey triggerKey = new TriggerKey(jobKey.getName(), jobKey.getGroup());
                    Scheduler scheduler = schedulerFactoryBean.getScheduler();
                    CronTrigger cronTrigger = (CronTrigger) scheduler.getTrigger(triggerKey);
                    String oldCron = cronTrigger.getCronExpression();
                    if (!oldCron.equalsIgnoreCase(dto.getCron())) {
                        job.setCron(dto.getCron());
                        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(dto.getCron());
                        CronTrigger trigger = TriggerBuilder.newTrigger()
                                .withIdentity(jobKey.getName(), jobKey.getGroup())
                                .withSchedule(cronScheduleBuilder)
                                .usingJobData(this.getJobDataMap(job))
                                .build();
                        scheduler.rescheduleJob(triggerKey, trigger);
                        this.jobInfoMapper.save(job);
                    }
                } catch (Exception e) {
                    log.error("printStackTrace", e);
                }
            } else {
                log.info("Job jump name : {} , Because {} status is {}", job.getName(), job.getName(), job.getStatus());
                return Result.error("modify failure , because the job is closed");
            }
        }
        return Result.success();
    }

    /**
     * 暂停所有定时任务
     */
    public Result suspendAll() {
        synchronized (log) {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            try {
                Set<JobKey> set = scheduler.getJobKeys(GroupMatcher.anyGroup());
                scheduler.pauseJobs(GroupMatcher.anyGroup());                               //暂停所有JOB
                for (JobKey jobKey : set) {                                                 //删除从数据库中注册的所有JOB
                    scheduler.unscheduleJob(TriggerKey.triggerKey(jobKey.getName(), jobKey.getGroup()));
                    scheduler.deleteJob(jobKey);
                }
            } catch (SchedulerException e) {
                log.error("exception:{}", e);
                return Result.error("Pause scheduled task failed");
            }

/*            for (JobInfo job : this.loadJobs()) {
                if (Constant.STATUS_OPEN.equals(job.getStatus())) {
                    job.setStatus(Constant.STATUS_CLOSE);
                    this.jobInfoMapper.save(job);
                }
            }*/
        }
        return Result.success();
    }

    /**
     * 任务 - 暂停一个定时任务
     */
    public Result suspend(Long id) {
        JobInfo jobInfo = this.getById(id);
        if (Objects.isNull(jobInfo)) {
            Result.error(ResponseCode.data_not_exist.getMessage());
        }
        try {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            JobKey jobKey = this.getJobKey(jobInfo);

            JobDetail jobDetail = this.getJobDetail(jobInfo.getJobClassName(), jobKey, jobInfo.getDescription(),
                    this.getJobDataMap(jobInfo));
            if (jobDetail == null)
                Result.error(ResponseCode.data_not_exist.getMessage());
            scheduler.deleteJob(jobKey);
   /*         jobInfo.setStatus(Constant.STATUS_CLOSE);
            this.jobInfoMapper.save(jobInfo);*/
        } catch (Exception e) {
            log.error("exception:{}", e);
            return Result.error("Pause scheduled task failed");
        }
        return Result.success();
    }

    /**
     * 删除定时任务
     *
     * @param id
     * @return
     */
    public Result delete(Long id) {
        JobInfo jobInfo = this.getById(id);
        if (Objects.isNull(jobInfo)) {
            Result.error(ResponseCode.data_not_exist.getMessage());
        }
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        JobKey jobKey = this.getJobKey(jobInfo);

        JobDetail jobDetail = null;
        try {
            jobDetail = this.getJobDetail(jobInfo.getJobClassName(), jobKey, jobInfo.getDescription(),
                    this.getJobDataMap(jobInfo));
            if (jobDetail == null)
                Result.error(ResponseCode.data_not_exist.getMessage());
            scheduler.deleteJob(jobKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        jobInfo.setStatus(Constant.STATUS_CLOSE);
        this.jobInfoMapper.save(jobInfo);
        return Result.success(id);
    }
}