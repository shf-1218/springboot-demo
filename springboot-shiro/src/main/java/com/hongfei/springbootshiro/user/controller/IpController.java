package com.hongfei.springbootshiro.user.controller;

import com.hongfei.springbootshiro.common.model.PageInfo;
import com.hongfei.springbootshiro.common.model.Result;
import com.hongfei.springbootshiro.user.model.IpForbidden;
import com.hongfei.springbootshiro.user.service.IpService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * @author hongfei.shen
 * @date 2019/06/17
 */
@RestController
@RequestMapping("ip")
public class IpController {
    @Resource
    private IpService ipService;

    /**
     * 查询ip列表
     *
     * @param page
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "查询ip列表", httpMethod = "GET", produces = "application/json", response = PageInfo.class)
    //@RequiresPermissions("ip:list")
    @GetMapping(value = "list")
    public PageInfo list(@RequestParam(defaultValue = "1") int page,
                         @RequestParam(defaultValue = "30") int pageSize) {
        PageInfo pageInfo = this.ipService.selectAll(page, pageSize);
        return pageInfo;
    }

    /**
     * 插入ip
     *
     * @return
     */
    @ApiOperation(value = "插入ip", httpMethod = "POST", produces = "application/json", response = Result.class)
    //@RequiresPermissions("ip:insert")
    @PostMapping(value = "insert")
    public Result insert(@RequestBody IpForbidden ipForbidden) {
        Result result = this.ipService.insert(ipForbidden);
        return result;
    }

    /**
     * 更新ip
     *
     * @return
     */
    @ApiOperation(value = "更新ip", httpMethod = "POST", produces = "application/json", response = Result.class)
    //@RequiresPermissions("ip:update")
    @PostMapping(value = "update")
    public Result update(@RequestBody IpForbidden ipForbidden) {
        Result result = this.ipService.update(ipForbidden);
        return result;
    }

    /**
     * 删除ip
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "删除ip", httpMethod = "DELETE", produces = "application/json", response = Result.class)
    // @RequiresPermissions("ip:delete")
    @DeleteMapping(value = "delete")
    public Result ipDelete(@RequestParam Long id) {
        Result result = this.ipService.delete(id);
        return result;
    }

    /**
     * 查询ip详情
     *
     * @return
     */
    @ApiOperation(value = "查询ip详情", httpMethod = "GET", produces = "application/json", response = Result.class)
    //@RequiresPermissions("data:select")
    @GetMapping(value = "get")
    public Result get(@RequestParam Long id) {
        Result result = this.ipService.getById(id);
        return result;
    }





}
