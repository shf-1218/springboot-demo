package com.hongfei.springbootswagger.controller;


import com.hongfei.springbootswagger.entity.User;
import com.hongfei.springbootswagger.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: springboot-demo
 * @Date: 2019-04-19 22:17
 * @Author: Mr.Shen
 * @Description: 用户接口层
 */
@Api("用户管理页面")
@RestController
@RequestMapping("user")
public class UserController {
    @Resource
    private UserService userService;

    @ApiOperation(value = "用户列表")
    @GetMapping("list")
    public List<User> list() {
        List<User> userList = this.userService.selectAll();
        return userList;
    }

    @ApiOperation(value = "筛选过滤username")
    @ApiImplicitParam(name = "userName",value = "用户名称",required = true,dataType = "String",paramType = "query")
    @GetMapping("findAllByUserName")
    public List<User> findAllByUserName(@RequestParam("userName") String userName) {
        return this.userService.findAllByUserName(userName);
    }

    @ApiOperation(value = "用户详情页")
    @ApiImplicitParam(name = "id",value = "用户id",required = true,dataType = "int",paramType = "query")
    @GetMapping(value = "get")
    public User get(@RequestParam("id") Long id) {
        User user = this.userService.selectById(id);
        return user;
    }

    @ApiOperation(value = "新增用户")
    @ApiImplicitParam(name = "user", value = "用户实体User", required = true, dataType = "User")
    @PostMapping("insert")
    public void insert(@RequestBody User user) {
        this.userService.insert(user);
    }

    @ApiOperation(value = "编辑用户")
    @ApiImplicitParam(name = "user", value = "用户实体User", required = true, dataType = "User")
    @PostMapping("update")
    public void update(@RequestBody User user) {
        this.userService.update(user);
    }

    @ApiOperation("删除用户")
    @ApiImplicitParam(name = "id",value = "用户id",required = true,dataType = "int",paramType = "query")
    @DeleteMapping("delete")
    public void delete(@RequestParam("id") Long id) {
        this.userService.delete(id);
    }
}
