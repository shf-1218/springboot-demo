package com.hongfei.springbootshiro.common.aop;


import com.hongfei.springbootshiro.common.exception.ForbiddenIpException;
import com.hongfei.springbootshiro.common.model.ResponseCode;
import com.hongfei.springbootshiro.user.service.DataItemService;
import com.hongfei.springbootshiro.user.service.IpService;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * ip拦截前置拦截器
 */
@Aspect
@Component
@Order(1) //order值越小越先执行
public class ForbiddenIpAOP {

    private static Logger log = LoggerFactory.getLogger(ForbiddenIpAOP.class);

    @Autowired
    private DataItemService dataItemService;
    @Resource
    private IpService ipService;

    @Before("@within(org.springframework.web.bind.annotation.RequestMapping)")
    public void forbiddenIp() throws ForbiddenIpException {
        if ("true".equals(dataItemService.selectDataItemByKey("ip_forbidden"))) {
            log.debug("open ip intercepter : {}", true);
            HttpServletRequest request =
                    ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            String remoteAddr = request.getRemoteAddr();
            if (ipService.isForbiddenIp(remoteAddr)) {
                log.error("this {}  ip is  forbidden ", remoteAddr);
                throw new ForbiddenIpException(ResponseCode.forbidden_ip.getMessage());
            }
        }
    }
}
