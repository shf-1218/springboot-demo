package com.hongfei.springbootshiro.user.controller;

import com.hongfei.springbootshiro.common.model.ResponseCode;
import com.hongfei.springbootshiro.common.model.Result;
import com.hongfei.springbootshiro.user.model.dto.UserNamePassWord;
import com.hongfei.springbootshiro.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author hongfei.shen
 * @date 2019/06/04
 */
@Api(value = "系统登录模块")
@RestController
public class LoginController {
    @Resource
    private UserService userService;

    /**
     * 登录
     *
     * @return
     */
    @ApiOperation(value = "登录", httpMethod = "POST", produces = "application/json", response = Result.class)
    @PostMapping(value = "login")
    public Result login(@RequestBody UserNamePassWord userNamePassword) throws Exception {
        Result result = this.userService.login(userNamePassword);
        return result;
    }

    /**
     * 退出
     *
     * @return
     */
    @ApiOperation(value = "退出", httpMethod = "GET", produces = "application/json", response = Result.class)
    @GetMapping(value = "logout")
    public Result logout() {
        SecurityUtils.getSubject().logout();
        return Result.success();
    }
}
