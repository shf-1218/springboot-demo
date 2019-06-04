package com.hongfei.springbootshiro.user.service;

import com.github.pagehelper.PageHelper;
import com.hongfei.springbootshiro.common.model.PageInfo;
import com.hongfei.springbootshiro.common.model.ResponseCode;
import com.hongfei.springbootshiro.common.model.Result;
import com.hongfei.springbootshiro.user.common.Constant;
import com.hongfei.springbootshiro.user.mapper.RoleMapper;
import com.hongfei.springbootshiro.user.model.Role;
import com.hongfei.springbootshiro.user.model.dto.RoleDto;
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
public class RoleService {
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private RolePermissionService rolePermissionService;
    @Resource
    private RoleOrganizationService roleOrganizationService;

    public boolean isExistName(Long id, String name) {
        return this.roleMapper.isExistName(id, name);
    }


    public Result insert(RoleDto roleDto) {
        if (this.isExistName(roleDto.getId(), roleDto.getName())) {
            return Result.error(ResponseCode.name_already_exist.getMessage());
        }
        Role role = new Role();
        BeanUtils.copyProperties(roleDto, role);
        this.roleMapper.insert(role);
        this.rolePermissionService.insert(roleDto.getPermissionIdList(), role);
        return Result.success(role);
    }

    public Result update(RoleDto roleDto) {
        Role oldRole = this.selectById(roleDto.getId());
        if (oldRole == null) {
            return Result.error(ResponseCode.data_not_exist.getMessage());
        }
        if (this.isExistName(roleDto.getId(), roleDto.getName())) {
            return Result.error(ResponseCode.name_already_exist.getMessage());
        }
        if (Constant.STATUS_DISABLE == roleDto.getStatus() && !checkStatus(roleDto.getId())) {
            return Result.error(ResponseCode.data_is_in_use.getMessage());
        }
        this.rolePermissionService.deleteByRoleId(roleDto.getId());
        Role role = new Role();
        BeanUtils.copyProperties(roleDto, role);
        this.roleMapper.update(role);
        this.rolePermissionService.insert(roleDto.getPermissionIdList(), role);
        return Result.success(role);
    }


    public Result updateStatus(UpdateStatus updateStatus) {
        Role oldRole = this.selectById(updateStatus.getId());
        if (oldRole == null) {
            return Result.error(ResponseCode.data_not_exist.getMessage());
        }
        if (Constant.STATUS_DISABLE == updateStatus.getStatus() && !checkStatus(updateStatus.getId())) {
            return Result.error(ResponseCode.data_is_in_use.getMessage());
        }
        this.roleMapper.updateStatus(updateStatus.getId(), updateStatus.getStatus());
        return Result.success();
    }

    private boolean checkStatus(Long id) {
        boolean flag = true;
        if (!this.roleOrganizationService.checkStatusByRoleId(id)) {
            flag = false;
        }
        return flag;
    }


    public Role selectById(Long id) {
        return this.roleMapper.selectById(id);
    }

    public PageInfo selectPage(int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        List<Role> roleList = this.roleMapper.selectAllByParam();
        return new PageInfo(roleList);
    }

    public Result delete(Long id) {
        Role oldRole = this.selectById(id);
        if (oldRole == null) {
            return Result.error(ResponseCode.data_not_exist.getMessage());
        }
        if (Constant.STATUS_DISABLE != oldRole.getStatus()) {
            return Result.error("停用状态的才可以删除");
        }
        this.roleMapper.updateStatus(id, Constant.STATUS_DELETE);
        this.rolePermissionService.deleteByRoleId(id);
        this.roleOrganizationService.deleteByRoleId(id);
        return Result.success(id);
    }

    public Result getById(Long id) {
        Role oldRole = this.selectById(id);
        if (oldRole == null) {
            return Result.error(ResponseCode.data_not_exist.getMessage());
        }
        RoleDto roleDto = new RoleDto();
        BeanUtils.copyProperties(oldRole, roleDto);
        List<Long> permissionIdList = this.rolePermissionService.selectByRoleId(id);
        roleDto.setPermissionIdList(permissionIdList);
        return Result.success(roleDto);
    }
}
