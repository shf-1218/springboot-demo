package com.hongfei.springbootshiro.common.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;

/**
 * @author hongfei.shen
 * @date 2018/12/14
 */
@ApiIgnore
@RestController
@RequestMapping("${server.error.path:/error}")
public class GlobalErrorController implements ErrorController {
    @Autowired
    private ErrorInfoBuilder errorInfoBuilder;//错误信息的构建工具.


    /**
     * 获取错误控制器的映射路径.
     */
    @Override
    public String getErrorPath() {
        return errorInfoBuilder.getErrorProperties().getPath();
    }

    /**
     * 情况2：其它预期类型 则返回详细的错误信息(JSON).
     */
    @RequestMapping
    public ErrorInfo error(HttpServletRequest request) {
        return errorInfoBuilder.getErrorInfo(request);
    }
}
