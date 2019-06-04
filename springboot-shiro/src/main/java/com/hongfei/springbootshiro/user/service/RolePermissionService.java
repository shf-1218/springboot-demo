package com.hongfei.springbootshiro.user.service;

import com.hongfei.springbootshiro.user.mapper.RolePermissionMapper;
import com.hongfei.springbootshiro.user.model.Role;
import com.hongfei.springbootshiro.user.model.RolePermission;
import com.hongfei.springbootshiro.user.model.dto.RoleDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author hongfei.shen
 * @date 2019/05/29
 */
@Slf4j
@Service
public class RolePermissionService {
    @Resource
    private RolePermissionMapper rolePermissionMapper;

    public void insert(List<Long> permissionList, Role role) {
        permissionList.stream().forEach(e -> {
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(role.getId());
            rolePermission.setPermissionId(e);
            this.rolePermissionMapper.insert(rolePermission);
        });
    }

    public void deleteByRoleId(Long roleId) {
        this.rolePermissionMapper.deleteByRoleId(roleId);
    }

    public List<Long> selectByRoleId(Long roleId) {
        return this.rolePermissionMapper.selectByRoleId(roleId);
    }

    public boolean checkStatus(Long permissionId) {
        boolean flag = false;
        List<RolePermission> rolePermissionList = this.rolePermissionMapper.selectBypermissionId(permissionId);
        if (rolePermissionList.isEmpty()) {
            flag = true;
        }
        return flag;
    }

    public void deleteByPermissionId(Long permissionId) {
        this.rolePermissionMapper.deleteByPermissionId(permissionId);
    }
}
