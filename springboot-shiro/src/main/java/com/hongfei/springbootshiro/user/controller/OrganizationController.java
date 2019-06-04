package com.hongfei.springbootshiro.user.controller;

import com.hongfei.springbootshiro.common.model.PageInfo;
import com.hongfei.springbootshiro.common.model.ResponseCode;
import com.hongfei.springbootshiro.common.model.Result;
import com.hongfei.springbootshiro.user.model.dto.OrganizationDto;
import com.hongfei.springbootshiro.user.model.dto.UpdateStatus;
import com.hongfei.springbootshiro.user.service.OrganizationService;
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

@Api(value = "组织机构模块")
@Slf4j
@RestController
@RequestMapping("organization")
public class OrganizationController {
    @Resource
    private OrganizationService organizationService;

    /**
     * 查询机构列表
     *
     * @param page 起始页码
     * @return
     */
    @ApiOperation(value = "查询机构列表", httpMethod = "GET", produces = "application/json", response = PageInfo.class)
    //@RequiresPermissions("organization:list")
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public PageInfo list(@RequestParam(value = "page", defaultValue = "1") int page,
                         @RequestParam(value = "pageSize", defaultValue = "15") int pageSize,
                         @RequestParam(value = "parentId", defaultValue = "1") Long parentId) {
        PageInfo pageInfo = organizationService.selectPage(page, pageSize, parentId);
        return pageInfo;
    }

    /**
     * 新增机构
     *
     * @return
     */
    @ApiOperation(value = "新增机构", httpMethod = "POST", produces = "application/json", response = Result.class)
    //@RequiresPermissions("organization:insert")
    @RequestMapping(value = "insert", method = RequestMethod.POST)
    public Result insert(@RequestBody OrganizationDto organizationDto) {
        this.organizationService.insert(organizationDto);
        return Result.success();
    }

    /**
     * 更新机构
     *
     * @return
     */
    @ApiOperation(value = "更新机构", httpMethod = "POST", produces = "application/json", response = Result.class)
    //@RequiresPermissions("organization:update")
    @PostMapping(value = "update")
    public Result update(@RequestBody OrganizationDto organizationDto) {
        Result result = this.organizationService.update(organizationDto);
        return result;
    }

    /**
     * 删除机构
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "删除机构", httpMethod = "DELETE", produces = "application/json", response = Result.class)
    //@RequiresPermissions("organization:delete")
    @DeleteMapping(value = "delete")
    public Result delete(@RequestParam Long id) {
        Result result = this.organizationService.delete(id);
        return result;
    }

    /**
     * 机构详情
     *
     * @return
     */
    @ApiOperation(value = "机构详情", httpMethod = "GET", produces = "application/json", response = Result.class)
    // @RequiresPermissions("organization:update")
    @GetMapping(value = "get")
    public Result get(@RequestParam Long id) {
        Result resul = this.organizationService.getById(id);
        return resul;
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
        Result result = organizationService.updateStatus(updateStatus);
        return result;
    }

}
