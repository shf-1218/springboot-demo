package com.hongfei.springbootshiro.user.model;

import java.util.Date;


import lombok.Data;



@Data
public class User {


    private Long id;

    /**
     * 登陆名
     */

    private String userName;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 密码
     */
    private String password;

    /**
     * 盐
     */
    private String salt;

    /**
     * Email
     */
    private String email;

    /**
     * 座机
     */
    private String phone;

    /**
     * 电话
     */
    private String mobile;
    /**
     * 数据状态,0:删除,1:正常,2:禁用账号
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