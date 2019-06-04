package com.hongfei.springbootshiro.user.model;

import java.util.Date;
import lombok.Data;

@Data
public class Role {
    /**
     * 角色ID
     */
    private Long id;

    /**
     * 角色名
     */
    private String name;

    /**
     * 角色描述
     */
    private String description;

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