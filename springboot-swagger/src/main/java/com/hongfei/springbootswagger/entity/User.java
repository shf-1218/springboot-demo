package com.hongfei.springbootswagger.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @program: springboot-demo
 * @Date: 2019-04-20 12:41
 * @Author: Mr.Shen
 * @Description: 用户实体
 */
@ApiModel
@Entity(name = "t_user")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(name = "id",value = "主键Id",dataType = "Long")
    private Long id;
    @ApiModelProperty(name = "username",required = true,value = "用户名",dataType = "String")
    private String username;
    @ApiModelProperty(name = "password",required = true,value = "密码",dataType = "String")
    private String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
