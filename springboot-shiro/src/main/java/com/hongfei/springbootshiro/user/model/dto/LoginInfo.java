package com.hongfei.springbootshiro.user.model.dto;

import com.hongfei.springbootshiro.user.model.Permission;
import com.hongfei.springbootshiro.user.model.UserRoleOrganization;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hongfei.shen
 * @date 2019/06/13
 */
@Data
public class LoginInfo implements Serializable {
    private Long id;

    //登陆名
    private String userName;

    //昵称
    private String nickName;

    // email :邮箱
    private String email;

    // phone :电话
    private String phone;

    // address :地址
    private String address;

    private List<UserRoleOrganization> jobs = new ArrayList<>();

    private List<Permission> permissions = new ArrayList<>();

}
