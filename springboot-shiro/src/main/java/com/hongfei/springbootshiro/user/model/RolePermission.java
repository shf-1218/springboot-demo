package com.hongfei.springbootshiro.user.model;

import java.util.Date;


import lombok.Data;



@Data
public class RolePermission {
    /**
     * 角色权限关系ID
     */


    private Long id;

    /**
     * 角色ID
     */

    private Long roleId;

    /**
     * 权限ID
     */

    private Long permissionId;
    /**
     * 状态
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