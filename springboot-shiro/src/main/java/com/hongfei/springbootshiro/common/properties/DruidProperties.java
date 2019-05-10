package com.hongfei.springbootshiro.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @program: springboot-demo
 * @Date: 2019-04-23 20:53
 * @Author: Mr.Shen
 * @Description: Druid注入的配置参数
 */
@Data
@ConfigurationProperties(prefix = "spring.datasource")
public class DruidProperties {
    private String url;

    private String driverClassName;

    private String username;

    private String password;

    private String publicKey;

    private Integer initialSize;

    private Integer minIdle;

    private Integer maxActive;

    private Long maxWait;

    private Long timeBetweenEvictionRunsMillis;

    private Long minEvictableIdleTimeMillis;

    private Boolean poolPreparedStatements;

    private Integer maxPoolPreparedStatementPerConnectionSize;

    private String validationQuery;

    private String filters;

}
