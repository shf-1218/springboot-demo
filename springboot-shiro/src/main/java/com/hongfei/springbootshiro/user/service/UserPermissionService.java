package com.hongfei.springbootshiro.user.service;

import com.hongfei.springbootshiro.user.mapper.UserPermissionMapper;
import com.hongfei.springbootshiro.user.model.UserPermission;
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
public class UserPermissionService {
    @Resource
    private UserPermissionMapper userPermissionMapper;

    public void insert(List<Long> permissionIdList, Long userId) {
        permissionIdList.stream().forEach(e -> {
            UserPermission userPermission = new UserPermission();
            userPermission.setUserId(userId);
            userPermission.setPermissionId(e);
            this.userPermissionMapper.insert(userPermission);
        });
    }


    public boolean checkStatus(Long permissionId) {
        boolean flag = false;
        List<UserPermission> userPermissionList = this.userPermissionMapper.selectByPermissionId(permissionId);
        if (userPermissionList.isEmpty()) {
            flag = true;
        }
        return flag;
    }

    public void deleteByPermissionId(Long permissionId) {
        this.userPermissionMapper.deleteByPermissionId(permissionId);
    }

    public void deleteByUserId(Long userId) {
        this.userPermissionMapper.deleteByUserId(userId);
    }

    public List<UserPermission> selectByUserId(Long userId) {
        this.userPermissionMapper.selectByUserId(userId);
        return null;
    }
}
