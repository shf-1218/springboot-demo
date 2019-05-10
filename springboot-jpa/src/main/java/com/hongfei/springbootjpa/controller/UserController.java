package com.hongfei.springbootjpa.controller;


import com.hongfei.springbootjpa.entity.User;
import com.hongfei.springbootjpa.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: springboot-demo
 * @Date: 2019-04-19 22:17
 * @Author: Mr.Shen
 * @Description: 用户接口层
 */
@RestController
@RequestMapping("user")
public class UserController {
    @Resource
    private UserService userService;

    @GetMapping("list")
    public List<User> list() {
        List<User> userList = this.userService.selectAll();
        return userList;
    }

    @GetMapping("findAllByUserName")
    public List<User> findAllByUserName(@RequestParam("userName") String userName) {
        return this.userService.findAllByUserName(userName);
    }

    @GetMapping("get")
    public User get(@RequestParam("id") Long id) {
        User user = this.userService.selectById(id);
        return user;
    }

    @PostMapping("insert")
    public void insert(@RequestBody User user) {
        this.userService.insert(user);
    }

    @PostMapping("update")
    public void update(@RequestBody User user) {
        this.userService.update(user);
    }

    @DeleteMapping("delete")
    public void delete(@RequestParam("id") Long id) {
        this.userService.delete(id);
    }
}
