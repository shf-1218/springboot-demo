package com.hongfei.springbootshiro.user.controller;

import com.hongfei.springbootshiro.common.model.PageInfo;
import com.hongfei.springbootshiro.common.model.Result;
import com.hongfei.springbootshiro.user.model.dto.PasswordDto;
import com.hongfei.springbootshiro.user.model.dto.UpdateStatus;
import com.hongfei.springbootshiro.user.model.dto.UserDto;
import com.hongfei.springbootshiro.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author hongfei.shen
 * @date 2019/05/30
 */
@Api("用户模块")
@Slf4j
@RestController
@RequestMapping("user")
public class UserController {
    @Resource
    private UserService userService;

    /**
     * 查询用户列表
     *
     * @return
     */
    @ApiOperation(value = "查询用户列表", httpMethod = "GET", produces = "application/json", response = PageInfo.class)
    //@RequiresPermissions("user:list")
    @GetMapping(value = "list")
    public PageInfo list(@RequestParam(defaultValue = "1") int page,
                         @RequestParam(defaultValue = "15") int pageSize) {
        PageInfo pageInfo = this.userService.selectPage(page, pageSize);
        return pageInfo;
    }

    /**
     * 新增用户
     *
     * @return
     */
    @ApiOperation(value = "新增用户", httpMethod = "POST", produces = "application/json", response = Result.class)
    //@RequiresPermissions("user:insert")
    @PostMapping(value = "insert")
    public Result insert(@RequestBody UserDto userDto) {
        Result result = this.userService.insert(userDto);
        return result;
    }

    /**
     * 更新用户
     *
     * @return
     */
    @ApiOperation(value = "更新用户", httpMethod = "POST", produces = "application/json", response = Result.class)
    // @RequiresPermissions("user:update")
    @PostMapping(value = "update")
    public Result update(@RequestBody UserDto userDto) {
        Result result = this.userService.update(userDto);
        return result;
    }

    /**
     * 更新密码
     *
     * @return
     */
    @ApiOperation(value = "更新密码", httpMethod = "POST", produces = "application/json", response = Result.class)
    //   @RequiresPermissions("user:updatePassword")
    @PostMapping(value = "updatePassword")
    public Result updatePassword(PasswordDto passwordDto) {
        Result result = this.userService.updatePassword(passwordDto);
        return Result.success();
    }

    @ApiOperation(value = "删除用户", httpMethod = "DELETE", produces = "application/json", response = Result.class)
    // @RequiresPermissions("user:delete")
    @DeleteMapping(value = "delete")
    public Result delete(@RequestParam Long id) {
        Result result = this.userService.delete(id);
        return result;
    }

    @ApiOperation(value = "修改状态", httpMethod = "POST", produces = "application/json", response = Result.class)
    //@RequiresPermissions("user:forbidden")
    @PostMapping(value = "updateStatus")
    public Result updateStatus(@RequestBody UpdateStatus updateStatus) {
        this.userService.updateStatus(updateStatus);
        return Result.success();
    }

}
