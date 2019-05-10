package com.hongfei.springbootshiro.common.configuration;

import com.hongfei.springbootshiro.common.redis.RedisSessionDAO;
import com.hongfei.springbootshiro.common.redis.RedisShiroCacheManager;
import com.hongfei.springbootshiro.common.shiro.ShiroRealm;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.ShiroHttpSession;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @program: springboot-demo
 * @Date: 2019-04-23 21:32
 * @Author: Mr.Shen
 * @Description: shiro配置注入
 */
@Slf4j
@Configuration
public class ShiroConfiguration {


    /**
     * 修复Spring Boot整合shiro出现UnavailableSecurityManagerException 问题
     * 此处设置相当于在web.xml中增加filter
     */
    @Bean
    public FilterRegistrationBean<DelegatingFilterProxy> delegatingFilterProxy() {
        if(log.isDebugEnabled()){
            log.debug("ShiroConfiguration.delegatingFilterProxy()");
        }
        FilterRegistrationBean<DelegatingFilterProxy> filterRegistrationBean = new FilterRegistrationBean<DelegatingFilterProxy>();
        DelegatingFilterProxy proxy = new DelegatingFilterProxy();
        proxy.setTargetFilterLifecycle(true);
        proxy.setTargetBeanName("shiroFilter");
        filterRegistrationBean.setFilter(proxy);
        return filterRegistrationBean;
    }

    /**
     * shiro 核心
     * @return
     */
    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilter() {
        if(log.isDebugEnabled()){
            log.debug("ShiroConfiguration.shirFilter()");
        }
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 设置securityManager，其中注入了自定义的Realm
        shiroFilterFactoryBean.setSecurityManager(securityManager());

        // 登陆url 如果不设置值，默认会自动寻找Web工程根目录下的"/login.jsp"页面 或 "/login" 映射
        shiroFilterFactoryBean.setLoginUrl("/login");

        // 成功登陆后打开的url
        shiroFilterFactoryBean.setSuccessUrl("/index");

        // 授权失败跳转的页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/login");

        Map<String, Filter> filtersMap = shiroFilterFactoryBean.getFilters();
        // 添加过滤器，例如：验证码过滤器 KaptchaFilter
        shiroFilterFactoryBean.setFilters(filtersMap);

        // 权限过滤链
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        /*
         * rest： 比如/admins/user/**=rest[user],根据请求的方法，相当于/admins/user/**=perms[user：method] ,其中method为post，get，delete等。
         * port： 比如/admins/user/**=port[8081],当请求的url的端口不是8081是跳转到schemal：//serverName：8081?queryString,其中schmal是协议http或https等，serverName是你访问的host,8081是url配置里port的端口，queryString是你访问的url里的？后面的参数。
         * perms：比如/admins/user/**=perms[user：add：*],perms参数可以写多个，多个时必须加上引号，并且参数之间用逗号分割，比如/admins/user/**=perms["user：add：*,user：modify：*"]，当有多个参数时必须每个参数都通过才通过，想当于isPermitedAll()方法。
         * roles：比如/admins/user/**=roles[admin],参数可以写多个，多个时必须加上引号，并且参数之间用逗号分割，当有多个参数时，比如/admins/user/**=roles["admin,guest"],每个参数通过才算通过，相当于hasAllRoles()方法。//要实现or的效果看http://zgzty.blog.163.com/blog/static/83831226201302983358670/
         * anon： 比如/admins/**=anon 没有参数，表示可以匿名使用。
         * authc：比如/admins/user/**=authc表示需要认证才能使用，没有参数
         * authcBasic：比如/admins/user/**=authcBasic没有参数表示httpBasic认证
         * ssl：  比如/admins/user/**=ssl没有参数，表示安全的url请求，协议为https
         * user： 比如/admins/user/**=user没有参数表示必须存在用户，当登入操作时不做检查
         */
        filterChainDefinitionMap.put("/login.jsp", "anon");
        filterChainDefinitionMap.put("/test/checkAuthc", "authc");
        filterChainDefinitionMap.put("/test/**", "anon");
        // druid过滤
        filterChainDefinitionMap.put("/druid", "anon");
        // swagger过滤
        filterChainDefinitionMap.put("/swagger", "anon");
        filterChainDefinitionMap.put("/swagger/api/docs", "anon");
        filterChainDefinitionMap.put("/swagger-ui.html", "anon");
        filterChainDefinitionMap.put("/webjars/**", "anon");
        filterChainDefinitionMap.put("/swagger-resources/**", "anon");
        // 其他需要授权
        filterChainDefinitionMap.put("/*", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return shiroFilterFactoryBean;
    }
    /**
     * SecurityManager: 安全管理器，注入有Realm、SessionManager、RememberMeManager
     *
     * @return SecurityManager
     */
    @Bean
    public SecurityManager securityManager() {
        if(log.isDebugEnabled()){
            log.debug("ShiroConfiguration.securityManager()");
        }
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(shiroRealm());
        // 自定义缓存实现，使用 Redis
        defaultWebSecurityManager.setCacheManager(redisCacheManager());
        defaultWebSecurityManager.setSessionManager(sessionManager());
        defaultWebSecurityManager.setRememberMeManager(rememberMeManager());
        return defaultWebSecurityManager;
    }

    /**
     * 后台身份认证realm;
     */
    @Bean
    public ShiroRealm shiroRealm() {
        if(log.isDebugEnabled()){
            log.debug("ShiroConfiguration.shiroRealm()");
        }
        ShiroRealm shiroRealm = new ShiroRealm();
        shiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return shiroRealm;
    }

    /**
     * 凭证匹配器（由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行处理了）
     *
     * @return HashedCredentialsMatcher
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        // 使用 MD5 散列算法
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        // 散列次数
        hashedCredentialsMatcher.setHashIterations(2);
        return hashedCredentialsMatcher;
    }


    @Bean
    public SessionManager sessionManager() {
        if(log.isDebugEnabled()){
            log.debug("ShiroConfiguration.sessionManager()");
        }
        DefaultWebSessionManager defaultWebSessionManager = new DefaultWebSessionManager();
        //是否删除无效的，默认也是开启
        defaultWebSessionManager.setDeleteInvalidSessions(true);
        //是否开启 检测，默认开启
        defaultWebSessionManager.setSessionValidationSchedulerEnabled(true);
        defaultWebSessionManager.setSessionDAO(redisSessionDAO());
        return defaultWebSessionManager;
    }


    @Bean
    public RedisSessionDAO redisSessionDAO(){
        if(log.isDebugEnabled()){
            log.debug("ShiroConfiguration.redisSessionDAO()");
        }
        return new RedisSessionDAO();
    }

    @Bean
    public RedisShiroCacheManager redisCacheManager() {
        if(log.isDebugEnabled()){
            log.debug("ShiroConfiguration.redisCacheManager()");
        }
        return new RedisShiroCacheManager();
    }


    /**
     * rememberMeCookie: 记住自己的cookie
     *
     * @return SimpleCookie
     */
    @Bean
    public SimpleCookie rememberMeCookie() {
        //这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
        SimpleCookie simpleCookie = new SimpleCookie(ShiroHttpSession.DEFAULT_SESSION_ID_NAME);
        //记住我cookie生效时间30天 ,单位秒;
        simpleCookie.setMaxAge(259200);
        return simpleCookie;
    }


    /**
     * RememberMeManager: remenberMe的管理器，注入有Cookie
     *
     * @return CookieRememberMeManager
     */
    @Bean
    public CookieRememberMeManager rememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        return cookieRememberMeManager;
    }



    /**
     * Shiro生命周期处理器
     *
     * @return
     */
    @Bean(name = "lifecycleBeanPostProcessor")
    public static LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        if(log.isDebugEnabled()){
            log.debug("ShiroConfiguration.lifecycleBeanPostProcessor()");
        }
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 开启Shiro的注解(如@RequiresRoles,@RequiresPermissions 授权注解)
     * 需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
     *
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        if(log.isDebugEnabled()){
            log.debug("ShiroConfiguration.authorizationAttributeSourceAdvisor()");
        }
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

    /**
     * 自动创建代理类，若不添加，Shiro的注解可能不会生效。
     * @return
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        // 强制使用cglib，防止重复代理和可能引起代理出错的问题，
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }

}
