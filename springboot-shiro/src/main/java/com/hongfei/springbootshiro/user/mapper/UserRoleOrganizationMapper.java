package com.hongfei.springbootshiro.user.mapper;

import com.hongfei.springbootshiro.user.model.UserRoleOrganization;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface UserRoleOrganizationMapper {
    void deleteByUserId(@Param("userId") Long userId);

    List<UserRoleOrganization> selectByJobId(@Param("roleOrganizationId") Long jobId);

    void deleteByJobId(@Param("roleOrganizationId") Long jobId);

    void insert(UserRoleOrganization userRoleOrganization);

    List<UserRoleOrganization> selectByUserId(@Param("userId") Long userId);
}