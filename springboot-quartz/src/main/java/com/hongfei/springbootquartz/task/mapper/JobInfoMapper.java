package com.hongfei.springbootquartz.task.mapper;

import com.hongfei.springbootquartz.task.model.JobInfo;
import org.springframework.data.repository.CrudRepository;

/**
 * @author hongfei.shen
 * @date 2019/06/20
 */
public interface JobInfoMapper extends CrudRepository<JobInfo, Long> {

}
