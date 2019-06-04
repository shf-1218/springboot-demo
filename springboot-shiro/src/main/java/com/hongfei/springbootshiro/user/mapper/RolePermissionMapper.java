package com.hongfei.springbootshiro.user.mapper;

import com.hongfei.springbootshiro.user.model.RolePermission;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface RolePermissionMapper  {
    void deleteByRoleId(@Param("roleId") Long roleId);

    List<Long> selectByRoleId(@Param("roleId") Long roleId);

    List<RolePermission> selectBypermissionId(@Param("permissionId") Long permissionId);

    void deleteByPermissionId(@Param("permissionId") Long permissionId);

    int insert(RolePermission rolePermission);
}