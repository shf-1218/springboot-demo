package com.hongfei.springbootshiro.user.mapper;

import com.hongfei.springbootshiro.user.model.Permission;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionMapper {
    void updateStatus(@Param("id") Long id, @Param("status") int status);

    boolean isExistName(@Param("id") Long id, @Param("parentId") Long parentId, @Param("name") String name);

    boolean isExistCode(@Param("id") Long id, @Param("code") String code);

    List<Permission> selectAllByParam();

    Permission selectById(@Param("id") Long id);

    int update(Permission permission);

    int insert(Permission permission);
}