package com.hongfei.springbootshiro.user.model;

import java.util.Date;
import lombok.Data;

@Data
public class LoginStatus {
    /**
     * 主键
     */


    private Long id;

    /**
     * 用户id
     */

    private Long userId;

    /**
     * session id
     */

    private String sessionId;

    /**
     * session过期时间
     */

    private Date sessionExpires;

    /**
     * 登录名
     */
    private String userLoginName;

    /**
     * 上一次登录时间
     */
    private Date lastLoginTime;

    /**
     * 登录平台 1:web 2:android 3:ios
     */
    private Byte platform;

    /**
     * 创建时间
     */

    private Date ctime;

    /**
     * 更新时间
     */

    private Date mtime;

    /**
     * 数据状态,1:正常,2:删除
     */

    private Byte status;
}