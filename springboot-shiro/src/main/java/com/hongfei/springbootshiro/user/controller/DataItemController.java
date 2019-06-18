package com.hongfei.springbootshiro.user.controller;

import com.hongfei.springbootshiro.common.model.PageInfo;
import com.hongfei.springbootshiro.common.model.ResponseCode;
import com.hongfei.springbootshiro.common.model.Result;
import com.hongfei.springbootshiro.user.model.DataItem;
import com.hongfei.springbootshiro.user.service.DataItemService;
import com.hongfei.springbootshiro.user.service.IpService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.data.redis.core.index.PathBasedRedisIndexDefinition;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author hongfei.shen
 * @date 2019/06/17
 */
@Slf4j
@Api("字典模块")
@RestController
@RequestMapping("dataItem")
public class DataItemController {
    @Resource
    private DataItemService dataItemService;

    /**
     * 字典列表
     *
     * @param page     起始页码
     * @param pageSize 分页大小
     * @return
     */
    @ApiOperation(value = "字典列表", httpMethod = "GET", produces = "application/json", response = PageInfo.class)
    //@RequiresPermissions("data:list")
    @ResponseBody
    @GetMapping(value = "list")
    public PageInfo dataList(@RequestParam(defaultValue = "1") int page,
                             @RequestParam(defaultValue = "30") int pageSize) {
        PageInfo pageInfo = dataItemService.selectAll(page, pageSize);
        return pageInfo;
    }

    /**
     * 新增数据字典
     *
     * @return
     */
    @ApiOperation(value = "新增数据字典", httpMethod = "POST", produces = "application/json", response = Result.class)
    //@RequiresPermissions("insert")
    @ResponseBody
    @PostMapping(value = "insert")
    public Result insert(@RequestBody DataItem dataItem) {
        Result result = this.dataItemService.insert(dataItem);
        return result;
    }

    /**
     * 更新字典
     *
     * @return
     */
    @ApiOperation(value = "更新字典", httpMethod = "POST", produces = "application/json", response = Result.class)
    //@RequiresPermissions("update")
    @ResponseBody
    @PostMapping(value = "update")
    public Result dataUpdate(@RequestBody DataItem dataItem) {
        Result result = this.dataItemService.update(dataItem);
        return result;
    }

    /**
     * 查询字典详情
     *
     * @return
     */
    @ApiOperation(value = "查询字典详情", httpMethod = "GET", produces = "application/json", response = Result.class)
    //@RequiresPermissions("data:select")
    @ResponseBody
    @GetMapping(value = "get")
    public Result get(@RequestParam Long id) {
        this.dataItemService.getById(id);
        return Result.success();
    }

    /**
     * 删除字典
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "删除字典", httpMethod = "DELETE", produces = "application/json", response = Result.class)
    //@RequiresPermissions("data:delete")
    @ResponseBody
    @DeleteMapping(value = "delete")
    public Result delete(@RequestParam Long id) {
        this.dataItemService.delete(id);
        return Result.success();
    }

    @ApiOperation(value = "ip拦截开关", httpMethod = "GET", produces = "application/json", response = Result.class)
    @GetMapping(value = "intercept")
    public Result intercept(@RequestParam boolean open) {
        //启用
        if (open == true) {
            dataItemService.openIpIntercept();
        }
        //禁用
        if (open == false) {
            dataItemService.closeIpIntercept();
        }
        return Result.success();
    }

    @ApiOperation(value = "ip拦截开关状态", httpMethod = "GET", produces = "application/json", response = Result.class)
    @GetMapping(value = "intercept/status")
    public Result interceptStatus() {
        boolean ip_forbidden = this.dataItemService.selectIPForbiddenStatus();
        return Result.success(ip_forbidden);
    }
}
