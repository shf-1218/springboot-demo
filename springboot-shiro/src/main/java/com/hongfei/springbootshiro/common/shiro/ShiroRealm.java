package com.hongfei.springbootshiro.common.shiro;

import com.hongfei.springbootshiro.common.Constant;
import com.hongfei.springbootshiro.user.model.*;
import com.hongfei.springbootshiro.user.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @program: springboot-demo
 * @Date: 2019-04-23 21:52
 * @Author: Mr.Shen
 * @Description: 自定义shiro权限认证授权
 * * Principals(身份)
 * * Credentials(凭证)
 * * Authorization（授权）
 * * Authentication（认证/鉴权）
 */
@Slf4j
public class ShiroRealm extends AuthorizingRealm {
    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private UserService userService;
    @Resource
    private UserPermissionService userPermissionService;
    @Resource
    private PermissionService permissionService;
    @Resource
    private UserRoleOrganizationService userRoleOrganizationService;
    @Resource
    private RoleOrganizationService roleOrganizationService;
    @Resource
    private RoleService roleService;
    @Resource
    private RolePermissionService rolePermissionService;

    /**
     * 查询权限，授权
     * 此方法调用hasRole,hasPermission的时候才会进行回调.
     * <p>
     * 权限信息.(授权):
     * 1、如果用户正常退出，缓存自动清空；
     * 2、如果用户非正常退出，缓存自动清空；
     * 3、如果我们修改了用户的权限，而用户不退出系统，修改的权限无法立即生效。
     * （需要手动编程进行实现；放在service进行调用）
     * 在权限修改后调用realm中的方法，realm已经由spring管理，所以从spring中获取realm实例，调用clearCached方法；
     * :Authorization 是授权访问控制，用于对用户进行的操作授权，证明该用户是否允许进行当前操作，如访问某个链接，某个资源文件等。
     *
     * @param principalCollection 身份集合
     * @return 授权信息
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.debug("开始查询授权信息");
        Set<String> permissionSet = new HashSet<>();
        Set<String> roleSet = new HashSet<>();

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        String userName = (String) principalCollection.getPrimaryPrincipal();
        User user = this.userService.selectByUserName(userName);
        List<UserPermission> userPermissionList = userPermissionService.selectByUserId(user.getId());
        userPermissionList.stream().forEach(e -> {
            Permission permission = permissionService.selectById(e.getPermissionId());
            permissionSet.add(permission.getCode());
        });

        List<UserRoleOrganization> userRoleOrganizationList = userRoleOrganizationService.selectByUserId(user.getId());
        userRoleOrganizationList.stream().forEach(e->{
            RoleOrganization roleOrganization = roleOrganizationService.selectById(e.getRoleOrganizationId());
            Role role = roleService.selectById(roleOrganization.getRoleId());
            roleSet.add(role.getName());
            List<Long> permissionList = this.rolePermissionService.selectByRoleId(role.getId());
            permissionList.stream().forEach(p->{
                Permission permission = this.permissionService.selectById(p);
                permissionSet.add(permission.getCode());
            });
        });

        info.addRoles(roleSet);
        info.addStringPermissions(permissionSet);
        log.debug("角色信息: \n {}", roleSet.toString());
        log.debug("权限信息: \n{}", permissionSet.toString());
        return null;
    }

    /**
     * 校验权限,认证
     *
     * @param authenticationToken 认证Token，只是用户密码认证(UsernamePasswordToken)/ID密码认证(IdPasswordToken)
     * @return 认证信息
     * @throws AuthenticationException 认证异常
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        if (log.isDebugEnabled()) {
            log.info("后台登录：ShiroRealm.doGetAuthenticationInfo()");
        }
        //获取用户的输入的账号.
        String username = (String) authenticationToken.getPrincipal();
        User user = this.userService.selectByUserName(username);
        AuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(username, user.getPassword(),
                ByteSource.Util.bytes(user.getSalt()), getName());
        return authenticationInfo;
    }

    @Override
    protected void doClearCache(PrincipalCollection principals) {
        redisTemplate.delete(Constant.shiro_cache_prefix + principals.getPrimaryPrincipal().toString());
    }

}
