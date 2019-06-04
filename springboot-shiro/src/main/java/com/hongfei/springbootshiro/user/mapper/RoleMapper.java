package com.hongfei.springbootshiro.user.mapper;

import com.hongfei.springbootshiro.user.model.Role;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface RoleMapper {
    boolean isExistName(@Param("id") Long id, @Param("name") String name);

    void updateStatus(@Param("id") Long id, @Param("status") int status);

    Role selectById(@Param("id") Long id);

    int insert(Role role);

    int update(Role role);

    List<Role> selectAllByParam();
}