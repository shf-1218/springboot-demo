package com.hongfei.springbootshiro.user.model;

import java.util.Date;

import lombok.Data;

@Data
public class Permission {
    /**
     * 权限ID
     */
    private Long id;

    /**
     * 权限名
     */
    private String name;
    /**
     * 编码
     */
    private String code;
    /**
     * 权限描述
     */
    private String description;

    private Long parentId;

    /**
     * 数据状态0:删除,1:正常,2:停用
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