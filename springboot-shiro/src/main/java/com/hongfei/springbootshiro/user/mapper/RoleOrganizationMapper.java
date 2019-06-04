package com.hongfei.springbootshiro.user.mapper;

import com.hongfei.springbootshiro.user.model.RoleOrganization;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface RoleOrganizationMapper {
    boolean isExistName(@Param("id") Long id, @Param("name") String name);

    void updateStatus(@Param("id") Long id, @Param("status") int status);

    List<RoleOrganization> selectAllParam(@Param("organizationId") Long organizationId);

    List<RoleOrganization> selectByRoleId(@Param("roleId") Long roleId);

    void deleteByRoleId(@Param("roleId") Long roleId);

    List<RoleOrganization> selectByOrganizationId(@Param("organizationId")Long organizationId);

    void deleteByOrganizationId(@Param("organizationId") Long organizationId);

    RoleOrganization selectById(@Param("id") Long id);

    int insert(RoleOrganization roleOrganization);

    int update(RoleOrganization roleOrganization);
}