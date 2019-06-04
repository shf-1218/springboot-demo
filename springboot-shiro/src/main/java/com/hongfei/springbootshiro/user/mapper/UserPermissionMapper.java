package com.hongfei.springbootshiro.user.mapper;

import com.hongfei.springbootshiro.user.model.UserPermission;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserPermissionMapper  {
    void deleteByPermissionId(@Param("permissionId") Long permissionId);

    void deleteByUserId(@Param("userId") Long userId);

    List<UserPermission> selectByPermissionId(@Param("permissionId")Long permissionId);

    int insert(UserPermission userPermission);

    void selectByUserId(@Param("userId") Long userId);
}