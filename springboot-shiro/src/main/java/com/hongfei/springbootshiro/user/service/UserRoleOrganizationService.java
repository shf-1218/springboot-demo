package com.hongfei.springbootshiro.user.service;

import com.hongfei.springbootshiro.user.mapper.UserRoleOrganizationMapper;
import com.hongfei.springbootshiro.user.model.RolePermission;
import com.hongfei.springbootshiro.user.model.UserRoleOrganization;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author hongfei.shen
 * @date 2019/05/30
 */
@Slf4j
@Service
public class UserRoleOrganizationService {
    @Resource
    private UserRoleOrganizationMapper userRoleOrganizationMapper;

    public void insert(List<Long> jobIdList, Long userId) {
        jobIdList.stream().forEach(e -> {
            UserRoleOrganization userRoleOrganization = new UserRoleOrganization();
            userRoleOrganization.setUserId(userId);
            userRoleOrganization.setRoleOrganizationId(e);
            this.userRoleOrganizationMapper.insert(userRoleOrganization);
        });
    }


    public boolean checkStatus(Long jobId) {
        boolean flag = false;
        List<UserRoleOrganization> userRoleOrganizationList = this.userRoleOrganizationMapper.selectByJobId(jobId);
        if (userRoleOrganizationList.isEmpty()) {
            flag = true;
        }
        return flag;
    }

    public void deleteByJobId(Long jobId) {
        this.userRoleOrganizationMapper.deleteByJobId(jobId);
    }

    public void deleteByUserId(Long userId) {
        this.userRoleOrganizationMapper.deleteByUserId(userId);
    }

    public List<UserRoleOrganization> selectByUserId(Long userId) {
        List<UserRoleOrganization> userRoleOrganizationList = this.userRoleOrganizationMapper.selectByUserId(userId);
        return userRoleOrganizationList;
    }
}
