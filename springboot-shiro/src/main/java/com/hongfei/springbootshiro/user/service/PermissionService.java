package com.hongfei.springbootshiro.user.service;

import com.github.pagehelper.PageHelper;
import com.hongfei.springbootshiro.common.model.PageInfo;
import com.hongfei.springbootshiro.common.model.ResponseCode;
import com.hongfei.springbootshiro.common.model.Result;
import com.hongfei.springbootshiro.user.common.Constant;
import com.hongfei.springbootshiro.user.mapper.PermissionMapper;
import com.hongfei.springbootshiro.user.model.Permission;
import com.hongfei.springbootshiro.user.model.dto.PermissionDto;
import com.hongfei.springbootshiro.user.model.dto.UpdateStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author hongfei.shen
 * @date 2019/05/27
 */
@Slf4j
@Service
public class PermissionService {
    @Resource
    private PermissionMapper permissionMapper;
    @Resource
    private RolePermissionService rolePermissionService;
    @Resource
    private UserPermissionService userPermissionService;

    public PageInfo selectPage(int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        List<Permission> permissionList = this.permissionMapper.selectAllByParam();
        return new PageInfo(permissionList);
    }

    public boolean isExistName(Long id, Long parentId, String name) {
        return this.permissionMapper.isExistName(id, parentId, name);
    }

    public boolean isExistCode(Long id, String code) {
        return this.permissionMapper.isExistCode(id, code);
    }

    public Result insert(PermissionDto permissionDto) {
        Result result = checkExist(permissionDto);
        if (result != null) return result;
        Permission permission = new Permission();
        BeanUtils.copyProperties(permissionDto, permission);
        this.permissionMapper.insert(permission);
        return Result.success(permission);
    }

    private Result checkExist(PermissionDto permissionDto) {
        if (this.isExistName(permissionDto.getId(), permissionDto.getParentId(), permissionDto.getName())) {
            return Result.error("该分组下名称已存在");
        }
        if (this.isExistCode(permissionDto.getId(), permissionDto.getCode())) {
            return Result.error("该权限分组下编码已存在");
        }
        return null;
    }

    public Result update(PermissionDto permissionDto) {
        Permission oldPermission = this.selectById(permissionDto.getId());
        if (oldPermission == null) {
            return Result.error(ResponseCode.data_not_exist.getMessage());
        }
        if (oldPermission.getId() == permissionDto.getParentId()) {
            return Result.error("上级权限不能选择自己,请选择其他权限!");
        }
        if (Constant.STATUS_DISABLE == permissionDto.getStatus() && !checkStatus(permissionDto.getId())) {
            return Result.error(ResponseCode.data_is_in_use.getMessage());
        }
        Result result = checkExist(permissionDto);
        if (result != null) return result;
        Permission permission = new Permission();
        BeanUtils.copyProperties(permissionDto, permission);
        int id = this.permissionMapper.update(permission);
        return Result.success(id);
    }


    public Permission selectById(Long id) {
        return this.permissionMapper.selectById(id);
    }

    public Result delete(Long id) {
        Permission oldPermission = this.selectById(id);
        if (oldPermission == null) {
            return Result.error(ResponseCode.data_not_exist.getMessage());
        }
        if (Constant.STATUS_DISABLE != oldPermission.getStatus()) {
            return Result.error("停用状态的才可以删除");
        }
        this.permissionMapper.updateStatus(id, Constant.STATUS_DELETE);
        this.rolePermissionService.deleteByPermissionId(id);
        this.userPermissionService.deleteByPermissionId(id);
        return Result.success();
    }

    public Result getById(Long id) {
        Permission oldPermission = this.selectById(id);
        if (oldPermission == null) {
            return Result.error(ResponseCode.data_not_exist.getMessage());
        }
        return Result.success(oldPermission);
    }

    public Result updateStatus(UpdateStatus updateStatus) {
        Permission permission = this.selectById(updateStatus.getId());
        if (permission == null) {
            return Result.error(ResponseCode.data_not_exist.getMessage());
        }
        if (Constant.STATUS_DISABLE == updateStatus.getStatus() && !checkStatus(updateStatus.getId())) {
            return Result.error(ResponseCode.data_is_in_use.getMessage());
        }
        this.permissionMapper.updateStatus(updateStatus.getId(), updateStatus.getStatus());
        return Result.success(updateStatus);
    }

    private boolean checkStatus(Long id) {
        boolean flag = true;
        if (!this.rolePermissionService.checkStatus(id) && !this.userPermissionService.checkStatus(id)) {
            flag = false;
        }
        return flag;
    }


}
