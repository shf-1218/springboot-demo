package com.hongfei.springbootjdbc.model;

/**
 * @program: springboot-demo
 * @Date: 2019-04-19 21:33
 * @Author: Mr.Shen
 * @Description: 用户实体类
 */
public class User {
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
