package com.hongfei.springbootshiro.user.controller;

import com.hongfei.springbootshiro.common.model.PageInfo;
import com.hongfei.springbootshiro.common.model.Result;
import com.hongfei.springbootshiro.user.model.User;
import com.hongfei.springbootshiro.user.service.LoginStatusService;
import com.hongfei.springbootshiro.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.nutz.mvc.annotation.GET;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author hongfei.shen
 * @date 2019/06/17
 */
@Api("用户状态模块")
@RestController
@RequestMapping("loginStatus")
public class LoginStatuController {
    @Resource
    private LoginStatusService loginStatusService;
    @Resource
    private UserService userService;

    /**
     * 在线用户列表
     *
     * @param page     起始页码
     * @param pageSize 分页大小
     * @return
     */
    @ApiOperation(value = "在线用户列表", httpMethod = "GET", produces = "application/json", response = PageInfo.class)
    // @RequiresPermissions("user:loginStatu:list")
    @ResponseBody
    @GetMapping(value = "list")
    public PageInfo list(@RequestParam(defaultValue = "1") int page,
                         @RequestParam(defaultValue = "30") int pageSize) {
        PageInfo pageInfo = this.loginStatusService.selectLoginStatus(page, pageSize);
        return pageInfo;
    }

    /**
     * 强制用户下线
     *
     * @param userIds 用户ids
     * @return
     */
    @ApiOperation(value = "强制用户下线", httpMethod = "GET", produces = "application/json", response = Result.class)
    // @RequiresPermissions("user:loginout")
    @ResponseBody
    @GetMapping(value = "forceLogout")
    public Result forceLogout(@RequestParam String userIds) {
        String[] ids = userIds.split(",");
        for (String id : ids) {
            this.userService.forceLogout(Long.parseLong(id));
        }
        return Result.success();
    }
}
