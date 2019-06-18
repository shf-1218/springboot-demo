package com.hongfei.springbootshiro.user.service;

import com.github.pagehelper.PageHelper;
import com.hongfei.springbootshiro.common.model.PageInfo;
import com.hongfei.springbootshiro.common.model.ResponseCode;
import com.hongfei.springbootshiro.common.model.Result;
import com.hongfei.springbootshiro.common.utils.MD5Util;
import com.hongfei.springbootshiro.user.common.Constant;
import com.hongfei.springbootshiro.user.mapper.LoginStatusMapper;
import com.hongfei.springbootshiro.user.mapper.UserMapper;
import com.hongfei.springbootshiro.user.model.*;
import com.hongfei.springbootshiro.user.model.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author hongfei.shen
 * @date 2019/05/30
 */
@Slf4j
@Service
public class UserService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private UserRoleOrganizationService userRoleOrganizationService;
    @Resource
    private UserPermissionService userPermissionService;
    @Resource
    private PermissionService permissionService;
    @Resource
    private LoginStatusMapper loginStatusMapper;
    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private RoleOrganizationService roleOrganizationService;

    public boolean isExistName(Long id, String name) {
        return this.userMapper.isExistName(id, name);
    }

    public PageInfo selectPage(int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        List<User> userList = this.userMapper.selectAllByParam();
        return new PageInfo(userList);
    }

    public User selectById(Long id) {
        return this.userMapper.selectById(id);
    }


    public Result insert(UserDto userDto) {
        if (this.isExistName(userDto.getId(), userDto.getUserName())) {
            return Result.error(ResponseCode.name_already_exist.getMessage());
        }
        if ((!StringUtils.hasText(userDto.getPassword())) && userDto.getPassword().length() < 6) {
            return Result.error("请设置密码长度大于等于6");
        }
        User user = new User();
        BeanUtils.copyProperties(userDto, user);
        String salt = UUID.randomUUID().toString().replaceAll("-", "");
        user.setSalt(salt);
        user.setPassword(MD5Util.createPassword(user.getPassword(), salt, 2));
        this.userMapper.insert(user);
        userRoleOrganizationService.insert(userDto.getJobIdList(), user.getId());
        userPermissionService.insert(userDto.getPermissionIdList(), user.getId());
        return Result.success(user);
    }

    public Result update(UserDto userDto) {
        User oldUser = this.selectById(userDto.getId());
        if (oldUser == null) {
            return Result.error(ResponseCode.data_not_exist.getMessage());
        }
        if (this.isExistName(userDto.getId(), userDto.getUserName())) {
            return Result.error(ResponseCode.name_already_exist.getMessage());
        }
        User user = new User();
        BeanUtils.copyProperties(userDto, user);
        this.userMapper.update(user);
        userRoleOrganizationService.deleteByUserId(user.getId());
        userPermissionService.deleteByUserId(user.getId());
        userRoleOrganizationService.insert(userDto.getJobIdList(), user.getId());
        userPermissionService.insert(userDto.getPermissionIdList(), user.getId());
        clearAuthorizationInfoCacheByUserId(userDto.getId());
        return Result.success(user);
    }

    public Result updatePassword(PasswordDto passwordDto) {
        if ((!StringUtils.hasText(passwordDto.getNewPassword())) && passwordDto.getNewPassword().length() < 6) {
            return Result.error("请设置密码长度大于等于6");
        }
        if (!passwordDto.getNewPassword().equals(passwordDto.getRepeatNewPassword())) {
            return Result.error("两次输入的密码不一致!");
        }
        User oldUser = this.selectById(passwordDto.getId());
        if (oldUser == null) {
            return Result.error(ResponseCode.data_not_exist.getMessage());
        }
        String salt = UUID.randomUUID().toString().replaceAll("-", "");
        oldUser.setSalt(salt);
        oldUser.setPassword(MD5Util.createPassword(passwordDto.getNewPassword(), salt, 2));
        userMapper.update(oldUser);
        return Result.success(oldUser);
    }

    public Result delete(Long id) {
        User oldUser = this.selectById(id);
        if (oldUser == null) {
            return Result.error(ResponseCode.data_not_exist.getMessage());
        }
        this.userMapper.updateStatus(id, Constant.STATUS_DELETE);
        forceLogout(id);
        return Result.success(id);
    }

    public Result updateStatus(UpdateStatus updateStatus) {
        User oldUser = this.selectById(updateStatus.getId());
        if (oldUser == null) {
            return Result.error(ResponseCode.data_not_exist.getMessage());
        }
        this.userMapper.updateStatus(updateStatus.getId(), updateStatus.getStatus());
        if (Constant.STATUS_DISABLE == updateStatus.getStatus()) {
            this.forceLogout(updateStatus.getId());
        }
        return Result.success(updateStatus);
    }

    public User selectByUserName(String userName) {
        User user = this.userMapper.selectByLoginName(userName);
        return user;
    }

    public Result login(UserNamePassWord userNamePassword) {
        User user = this.selectByUserName(userNamePassword.getLoginName());
        if (user == null) {
            return Result.instance(ResponseCode.unknown_account.getCode(), ResponseCode.unknown_account.getMessage());
        }
        if (user.getStatus() != Constant.STATUS_ENABLE) {
            return Result.instance(ResponseCode.forbidden_account.getCode(),
                    ResponseCode.forbidden_account.getMessage());
        }
        Subject subject = SecurityUtils.getSubject();
        subject.login(new UsernamePasswordToken(userNamePassword.getLoginName(), userNamePassword.getPassword()));
        LoginInfo loginInfo = this.loginInfo(user, subject.getSession().getId(), userNamePassword.getPlatform());
        subject.getSession().setAttribute("loginInfo", loginInfo);
        log.debug("登录成功");
        return Result.success();
    }


    private LoginInfo loginInfo(User user, Serializable sessionId, int platform) {
        log.debug("sessionId is:{}", sessionId.toString());
        LoginInfo loginInfo = new LoginInfo();
        BeanUtils.copyProperties(user, loginInfo);
        List<UserPermission> userPermissionList = this.userPermissionService.selectByUserId(user.getId());
        List<Permission> permissionList = new ArrayList<>();
        userPermissionList.stream().forEach(e -> {
            Permission permission = this.permissionService.selectById(e.getPermissionId());
            permissionList.add(permission);
        });
        List<UserRoleOrganization> userRoleOrganizationList = userRoleOrganizationService.selectByUserId(user.getId());
        loginInfo.setJobs(userRoleOrganizationList);

        LoginStatus loginStatus = new LoginStatus();
        loginStatus.setUserId(user.getId());
        loginStatus.setUserLoginName(user.getUserName());
        loginStatus.setSessionId(sessionId.toString());
        loginStatus.setSessionExpires(new DateTime().plusDays(30).toDate());
        loginStatus.setPlatform(platform);

        LoginStatus oldLoginStatus = this.loginStatusMapper.selectByUserIdAndPlatform(user.getId(), platform);
        if (oldLoginStatus != null) {
            if (!oldLoginStatus.getSessionId().equals(sessionId.toString())) {
                redisTemplate.opsForValue().getOperations().delete(oldLoginStatus.getSessionId());
            }
            oldLoginStatus.setStatus(Constant.STATUS_DELETE);
            this.loginStatusMapper.update(oldLoginStatus);
            loginStatus.setLastLoginTime(oldLoginStatus.getCtime());
        }
        this.loginStatusMapper.insert(loginStatus);
        return loginInfo;
    }

    public void forceLogout(long userId) {
        List<LoginStatus> loginStatusList = this.loginStatusMapper.selectByUserId(userId);
        loginStatusList.stream().forEach(e -> {
            e.setStatus(Constant.STATUS_DELETE);
            this.loginStatusMapper.update(e);
            //delete session
            redisTemplate.opsForValue().getOperations().delete(e.getSessionId());
            redisTemplate.opsForValue().getOperations().delete(com.hongfei.springbootshiro.common.Constant.shiro_cache_prefix + e.getUserLoginName());
        });

        log.debug("force logout userId : {}", userId);
    }

    public void clearAuthorizationInfoCacheByUserId(Long userId) {
        User oldUser = this.selectById(userId);
        if (oldUser != null) {
            redisTemplate.opsForValue().getOperations().delete(com.hongfei.springbootshiro.common.Constant.shiro_cache_prefix + oldUser.getUserName());
        }
        log.debug("clear authorization info cache userId : {}", userId);
    }

    public void clearAuthorizationInfoByRoleId(Long roleId) {
        log.debug("clear authorization info cache by roleId: {}", roleId);
        List<RoleOrganization> roleOrganizationList = roleOrganizationService.selectByRoleId(roleId);
        roleOrganizationList.stream().forEach(roleOrganization -> {
            List<UserRoleOrganization> userRoleOrganizationList =
                    userRoleOrganizationService.selectByRoleOrganizationId(roleOrganization.getId());
            userRoleOrganizationList.stream().forEach(userRoleOrganization -> {
                User oldUser = this.selectById(userRoleOrganization.getUserId());
                if (oldUser != null) {
                    redisTemplate.opsForValue().getOperations().delete(com.hongfei.springbootshiro.common.Constant.shiro_cache_prefix + oldUser.getUserName());
                }
            });
        });
    }
}
