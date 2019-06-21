package com.hongfei.springbootquartz.task.model;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

/**
 * @author hongfei.shen
 * @date 2019/06/20
 */
@Entity
@Table(name = "JOB_INFO")
@Data
@Accessors(chain = true)
public class JobInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;          //job名称
    private String jobGroup;      //job组名
    private String jobClassName;  //执行类
    private String cron;          //执行的cron
    private String description;   //job描述信息
    private String status;        //job的执行状态,这里我设置为OPEN/CLOSE且只有该值为OPEN才会执行该Job

}
