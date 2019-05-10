package com.hongfei.springbooterror.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 * @program: springboot-demo
 * @Date: 2019-04-21 21:55
 * @Author: Mr.Shen
 * @Description: 统一异常处理器
 */
@ControllerAdvice
public class GlobalErrorHandler {

    private final static String DEFAULT_ERROR_VIEW = "error";


}
