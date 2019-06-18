package com.hongfei.springbootshiro.user.model;

import java.util.Date;

import lombok.Data;


@Data
public class UserPermission {


    private Long id;


    private Long userId;


    private Long permissionId;

    /**
     * 数据状态,0:删除,1:正常
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