package com.hongfei.springbootshiro.user.model.dto;

import lombok.Data;

/**
 * @author hongfei.shen
 * @date 2019/06/04
 */
@Data
public class UserNamePassWord {
    private String loginName;
    private String password;
    private int platform;
}
