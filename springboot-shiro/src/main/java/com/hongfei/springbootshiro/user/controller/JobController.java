package com.hongfei.springbootshiro.user.controller;

import com.hongfei.springbootshiro.common.model.PageInfo;
import com.hongfei.springbootshiro.common.model.Result;
import com.hongfei.springbootshiro.user.model.dto.RoleOrganizationDto;
import com.hongfei.springbootshiro.user.model.dto.UpdateStatus;
import com.hongfei.springbootshiro.user.service.RoleOrganizationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author hongfei.shen
 * @date 2019/05/29
 */

@Api(value = "职位模块")
@Slf4j
@RestController
@RequestMapping("job")
public class JobController {
    @Resource
    private RoleOrganizationService roleOrganizationService;

    /**
     * 职位列表
     *
     * @return
     */
    @ApiOperation(value = "查询职位列表", httpMethod = "GET", produces = "application/json", response = PageInfo.class)
    //@RequiresPermissions("job:list")
    @GetMapping(value = "list")
    public PageInfo list(@RequestParam(defaultValue = "1") int page,
                         @RequestParam(defaultValue = "15") int pageSize,
                         @RequestParam(defaultValue = "1") Long organizationId) {
        PageInfo pageInfo = roleOrganizationService.selectPage(page, pageSize, organizationId);
        return pageInfo;
    }

    /**
     * 新增职位
     *
     * @return
     */
    @ApiOperation(value = "新增职位", httpMethod = "POST", produces = "application/json", response = Result.class)
    // @RequiresPermissions("job:insert")
    @PostMapping(value = "insert")
    public Result insert(@RequestBody RoleOrganizationDto roleOrganizationDto) {
        Result result = this.roleOrganizationService.insert(roleOrganizationDto);
        return Result.success(result);
    }

    /**
     * 更新职位
     *
     * @return
     */
    @ApiOperation(value = "更新职位", httpMethod = "POST", produces = "application/json", response = Result.class)
    //@RequiresPermissions("job:update")
    @PostMapping(value = "update")
    public Result update(@RequestBody RoleOrganizationDto roleOrganizationDto) {
        Result result = this.roleOrganizationService.update(roleOrganizationDto);
        return result;
    }

    /**
     * 删除职位
     *
     * @param id id
     * @return
     */
    @ApiOperation(value = "删除职位", httpMethod = "DELETE", produces = "application/json", response = Result.class)
    //@RequiresPermissions("job:delete")
    @DeleteMapping(value = "delete")
    public Result delete(@RequestParam Long id) {
        Result result = this.roleOrganizationService.delete(id);
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
        Result result = roleOrganizationService.updateStatus(updateStatus);
        return result;
    }

    /**
     * 职位详情
     *
     * @return
     */
    @ApiOperation(value = "职位详情", httpMethod = "GET", produces = "application/json", response = Result.class)
    // @RequiresPermissions("role:update")
    @GetMapping(value = "get")
    public Result get(@RequestParam Long id) {
        Result resul = this.roleOrganizationService.getById(id);
        return resul;
    }

}
