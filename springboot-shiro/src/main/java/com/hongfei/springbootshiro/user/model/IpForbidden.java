package com.hongfei.springbootshiro.user.model;

import java.util.Date;

import lombok.Data;



@Data
public class IpForbidden {
    private Long id;

    /**
     * IP地址
     */
    private String ip;

    /**
     * 数据状态,0:删除,1:正常,2:停用
     */

    private Byte status;

    /**
     * 到期时间
     */
    private Date expireTime;

    /**
     * 说明
     */
    private String description;

    /**
     * 创建时间
     */

    private Date ctime;

    /**
     * 更新时间
     */

    private Date mtime;
}