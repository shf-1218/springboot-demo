package com.hongfei.springbootshiro.user.model;

import java.util.Date;

import lombok.Data;


@Data
public class DataGroup {


    private Long id;

    /**
     * 名称
     */

    private String name;

    /**
     * 描述
     */

    private String description;

    /**
     * 父级id
     */

    private Long parentId;

    /**
     * 创建时间
     */

    private Date ctime;

    /**
     * 更新时间
     */

    private Date mtime;

    /**
     * 数据状态,0:删除,1:正常,2:禁用
     */

    private Byte status;
}