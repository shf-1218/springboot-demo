package com.hongfei.springbootshiro.common.configuration;

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
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.timeout}")
    private int timeout;

    /**
     * 修复Spring Boot整合shiro出现UnavailableSecurityManagerException 问题
     * 此处设置相当于在web.xml中增加filter
     */
    @Bean
    public FilterRegistrationBean<DelegatingFilterProxy> delegatingFilterProxy() {
        if (log.isDebugEnabled()) {
            log.debug("ShiroConfiguration.delegatingFilterProxy()");
        }
        FilterRegistrationBean<DelegatingFilterProxy> filterRegistrationBean =
                new FilterRegistrationBean<DelegatingFilterProxy>();
        DelegatingFilterProxy proxy = new DelegatingFilterProxy();
        proxy.setTargetFilterLifecycle(true);
        proxy.setTargetBeanName("shiroFilter");
        filterRegistrationBean.setFilter(proxy);
        return filterRegistrationBean;
    }

    /**
     * shiro 核心
     *
     * @return
     */
    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilter() {
        if (log.isDebugEnabled()) {
            log.debug("ShiroConfiguration.shirFilter()");
        }
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 设置securityManager，其中注入了自定义的Realm
        shiroFilterFactoryBean.setSecurityManager(securityManager());

        Map<String, Filter> filtersMap = shiroFilterFactoryBean.getFilters();
        // 添加过滤器，例如：验证码过滤器 KaptchaFilter
        shiroFilterFactoryBean.setFilters(filtersMap);

        // 权限过滤链
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();

        // druid过滤
        filterChainDefinitionMap.put("/druid", "anon");
        // swagger过滤
        filterChainDefinitionMap.put("/swagger", "anon");
        filterChainDefinitionMap.put("/swagger/api/docs", "anon");
        filterChainDefinitionMap.put("/swagger-ui.html", "anon");
        filterChainDefinitionMap.put("/webjars/**", "anon");
        filterChainDefinitionMap.put("/swagger-resources/**", "anon");
        // 其他需要授权
        filterChainDefinitionMap.put("/**", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return shiroFilterFactoryBean;
    }

    /**
     * 安全管理器，注入有Realm、SessionManager、RememberMeManager
     */
    @Bean
    public SecurityManager securityManager() {
        if (log.isDebugEnabled()) {
            log.debug("ShiroConfiguration.securityManager()");
        }
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(shiroRealm());
        // 自定义缓存实现 使用redis
        defaultWebSecurityManager.setCacheManager(cacheManager());
        // 自定义session管理 使用redis
        defaultWebSecurityManager.setSessionManager(sessionManager());

       // defaultWebSecurityManager.setRememberMeManager(rememberMeManager());
        return defaultWebSecurityManager;
    }

    /**
     * 后台身份认证realm;
     */
    @Bean
    public ShiroRealm shiroRealm() {
        if (log.isDebugEnabled()) {
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

    /**
     * 配置shiro redisManager
     * 使用的是shiro-redis开源插件
     *
     * @return
     */
    public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(host);
        redisManager.setPort(port);
        redisManager.setTimeout(1800);// 配置缓存过期时间
        redisManager.setTimeout(timeout);
        return redisManager;
    }

    /**
     * cacheManager 缓存 redis实现
     * 使用的是shiro-redis开源插件
     *
     * @return
     */
    public RedisCacheManager cacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        return redisCacheManager;
    }

    /**
     * RedisSessionDAO shiro sessionDao层的实现 通过redis
     * 使用的是shiro-redis开源插件
     */
    @Bean
    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        return redisSessionDAO;
    }

    @Bean
    public SessionManager sessionManager() {
        if (log.isDebugEnabled()) {
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


    /**
     * remenberMe的管理器，注入有Cookie
     */
    @Bean
    public CookieRememberMeManager rememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        return cookieRememberMeManager;
    }

    /**
     * 记住自己的cookie
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
     * Shiro生命周期处理器
     *
     * @return
     */
    @Bean(name = "lifecycleBeanPostProcessor")
    public static LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        if (log.isDebugEnabled()) {
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
        if (log.isDebugEnabled()) {
            log.debug("ShiroConfiguration.authorizationAttributeSourceAdvisor()");
        }
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

    /**
     * 自动创建代理类，若不添加，Shiro的注解可能不会生效。
     *
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
