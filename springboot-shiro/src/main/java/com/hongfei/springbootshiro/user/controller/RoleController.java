package com.hongfei.springbootshiro.user.controller;

import com.hongfei.springbootshiro.common.model.PageInfo;
import com.hongfei.springbootshiro.common.model.ResponseCode;
import com.hongfei.springbootshiro.common.model.Result;
import com.hongfei.springbootshiro.user.model.Role;
import com.hongfei.springbootshiro.user.model.dto.RoleDto;
import com.hongfei.springbootshiro.user.model.dto.UpdateStatus;
import com.hongfei.springbootshiro.user.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author hongfei.shen
 * @date 2019/05/29
 */
@Api("角色模块")
@Slf4j
@RequestMapping("role")
@RestController
public class RoleController {
    @Resource
    private RoleService roleService;

    /**
     * 角色列表
     *
     * @param page 起始页码
     * @return
     */
    @ApiOperation(value = "角色列表", httpMethod = "GET", produces = "application/json", response = PageInfo.class)
    // @RequiresPermissions("role:list")
    @GetMapping(value = "list")
    public PageInfo list(@RequestParam(defaultValue = "1") int page,
                         @RequestParam(defaultValue = "15") int pageSize) {
        PageInfo pageInfo = roleService.selectPage(page, pageSize);
        return pageInfo;
    }

    /**
     * 新增角色
     */
    @ApiOperation(value = "新增角色", httpMethod = "POST", produces = "application/json", response = Result.class)
    //@RequiresPermissions("role:insert")
    @PostMapping(value = "insert")
    public Result insert(@RequestBody RoleDto roleDto) {
        Result result = this.roleService.insert(roleDto);
        return result;
    }

    /**
     * 更新角色
     *
     * @return
     */
    @ApiOperation(value = "更新角色", httpMethod = "POST", produces = "application/json", response = Result.class)
    // @RequiresPermissions("role:update")
    @PostMapping(value = "update")
    public Result update(@RequestBody RoleDto roleDto) {
        Result result = this.roleService.update(roleDto);
        return result;
    }

    /**
     * 更新状态
     *
     * @return
     */
    @ApiOperation(value = "更新状态", httpMethod = "POST", produces = "application/json", response = Result.class)
    //@RequiresPermissions("permission:updateStatus")
    @PostMapping(value = "updateStatus")
    public Result updateStatus(@RequestBody UpdateStatus updateStatus) {
        Result result = roleService.updateStatus(updateStatus);
        return result;
    }
    /**
     * 角色详情
     *
     * @return
     */
    @ApiOperation(value = "角色详情", httpMethod = "GET", produces = "application/json", response = Result.class)
    // @RequiresPermissions("role:update")
    @GetMapping(value = "get")
    public Result get(@RequestParam Long id) {
        Result resul = this.roleService.getById(id);
        return resul;
    }

    /**
     * 删除角色
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "删除角色", httpMethod = "DELETE", produces = "application/json", response = Result.class)
    //@RequiresPermissions("role:delete")
    @DeleteMapping(value = "delete")
    public Result delete(@RequestParam Long id) {
        this.roleService.delete(id);
        return Result.success();
    }
}
