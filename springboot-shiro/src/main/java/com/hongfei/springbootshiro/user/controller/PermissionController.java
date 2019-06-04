package com.hongfei.springbootshiro.user.controller;

import com.hongfei.springbootshiro.common.model.PageInfo;
import com.hongfei.springbootshiro.common.model.Result;
import com.hongfei.springbootshiro.user.model.Permission;
import com.hongfei.springbootshiro.user.model.dto.PermissionDto;
import com.hongfei.springbootshiro.user.model.dto.UpdateStatus;
import com.hongfei.springbootshiro.user.service.PermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author hongfei.shen
 * @date 2019/05/27
 */
@Api("权限模块")
@Slf4j
@RestController
@RequestMapping("permission")
public class PermissionController {
    @Resource
    private PermissionService permissionService;

    /**
     * 查询权限列表
     *
     * @return
     */
    @ApiOperation(value = "查询权限列表", httpMethod = "GET", produces = "application/json", response = PageInfo.class)
    //@RequiresPermissions("permission:list")
    @GetMapping(value = "list")
    public PageInfo list(@RequestParam(defaultValue = "1") int page,
                         @RequestParam(defaultValue = "30") int pageSize) {
        PageInfo pageInfo = permissionService.selectPage(page, pageSize);
        return pageInfo;
    }

    /**
     * 新增权限
     *
     * @return
     */
    @ApiOperation(value = "新增权限", httpMethod = "POST", produces = "application/json", response = Result.class)
    //@RequiresPermissions("permission:insert")
    @PostMapping(value = "insert")
    public Result insert(@RequestBody PermissionDto permissionDto) {
        Result result = this.permissionService.insert(permissionDto);
        return result;
    }

    /**
     * 更新权限
     *
     * @return
     */
    @ApiOperation(value = "更新权限", httpMethod = "POST", produces = "application/json", response = Result.class)
    //@RequiresPermissions("permission:update")
    @PostMapping(value = "update")
    public Result update(@RequestBody PermissionDto permissionDto) {
        Result result = permissionService.update(permissionDto);
        return result;
    }

    /**
     * 删除权限
     *
     * @return
     */
    @ApiOperation(value = "删除权限", httpMethod = "DELETE", produces = "application/json", response = Result.class)
    //@RequiresPermissions("permission:delete")
    @DeleteMapping(value = "delete")
    public Result delete(@RequestParam Long id) {
        Result result = permissionService.delete(id);
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
        Result result = permissionService.updateStatus(updateStatus);
        return result;
    }

    /**
     * 权限详情
     *
     * @return
     */
    @ApiOperation(value = "权限详情", httpMethod = "GET", produces = "application/json", response = Result.class)
    // @RequiresPermissions("role:update")
    @GetMapping(value = "get")
    public Result get(@RequestParam Long id) {
        Result resul = this.permissionService.getById(id);
        return resul;
    }
}
