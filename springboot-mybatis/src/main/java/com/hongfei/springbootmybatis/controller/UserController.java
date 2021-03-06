package com.hongfei.springbootmybatis.controller;

import com.hongfei.springbootmybatis.model.User;
import com.hongfei.springbootmybatis.service.UserService;
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

    @GetMapping("countByUsername")
    public int countByUsername(@RequestParam("username") String username) {
        return this.userService.countByUsername(username);
    }
}