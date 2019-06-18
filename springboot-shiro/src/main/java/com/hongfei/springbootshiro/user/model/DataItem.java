package com.hongfei.springbootshiro.user.model;

import java.util.Date;

import lombok.Data;

@Data
public class DataItem {
    private Long id;


    /**
     * 名称
     */
    private String keyName;

    /**
     * 值
     */
    private String keyValue;

    /**
     * 描述
     */
    private String description;

    /**
     * 数据状态,0删除,1:正常,2:禁用
     */
    private int status;

    /**
     * 创建时间
     */
    private Date ctime;

    /**
     * 更新时间
     */
    private Date mtime;
}