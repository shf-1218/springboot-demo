package com.hongfei.springbootshiro.common.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * @program: springboot-demo
 * @Date: 2019-04-23 21:52
 * @Author: Mr.Shen
 * @Description: 自定义shiro权限认证授权
 *  * Principals(身份)
 *  * Credentials(凭证)
 *  * Authorization（授权）
 *  * Authentication（认证/鉴权）
 */
@Slf4j
public class ShiroRealm extends AuthorizingRealm {

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

        return null;
    }
}
