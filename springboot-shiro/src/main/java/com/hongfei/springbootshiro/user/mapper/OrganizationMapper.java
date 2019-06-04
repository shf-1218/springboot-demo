package com.hongfei.springbootshiro.user.mapper;

import com.hongfei.springbootshiro.user.model.Organization;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface OrganizationMapper {
    List<Organization> selectAllByParam(@Param("parentId") Long parentId);

    boolean isExistName(@Param("id") Long id, @Param("name") String name);

    void updateStatus(@Param("id") Long id, @Param("status") int status);

    Organization selectById(@Param("id") Long id);

    int update(Organization organization);

    int insert(Organization organization);
}