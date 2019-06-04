package com.hongfei.springbootshiro.user.model;

import java.util.Date;


import lombok.Data;



@Data
public class UserRoleOrganization {


    private Long id;


    private Long userId;

    private Long roleOrganizationId;


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