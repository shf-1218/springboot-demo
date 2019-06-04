package com.hongfei.springbootjpa.entity;

import javax.persistence.Entity;


import javax.persistence.Id;
import java.io.Serializable;

/**
 * @program: springboot-demo
 * @Date: 2019-04-20 12:41
 * @Author: Mr.Shen
 * @Description: 用户实体
 */
@Entity(name = "t_user")
public class User implements Serializable {
    @Id

    private Long id;
    private String username;
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
