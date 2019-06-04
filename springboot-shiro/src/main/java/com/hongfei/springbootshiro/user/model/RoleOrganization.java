package com.hongfei.springbootshiro.user.model;

import java.util.Date;


import lombok.Data;



@Data
public class RoleOrganization {


    private Long id;

    /**
     * 组织id
     */

    private Long organizationId;

    /**
     * 角色id
     */

    private Long roleId;
    /**
     * 名称
     */

    private String name;

    /**
     * 全称
     */

    private String fullName;

    /**
     * 描述
     */

    private String description;


    private Long parentId;

    /**
     * 数据状态,0:删除,1:正常,2:停用
     */

    private Byte status;
    /**
     * 创建时间
     */

    private Date ctime;

    /**
     * 更新时间
     */

    private Date mtime;
}