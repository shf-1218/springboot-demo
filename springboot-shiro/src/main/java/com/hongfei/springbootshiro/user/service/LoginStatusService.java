package com.hongfei.springbootshiro.user.service;

import com.github.pagehelper.PageHelper;
import com.hongfei.springbootshiro.common.model.PageInfo;
import com.hongfei.springbootshiro.user.common.Constant;
import com.hongfei.springbootshiro.user.mapper.LoginStatusMapper;
import com.hongfei.springbootshiro.user.model.LoginStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author hongfei.shen
 * @date 2019/06/17
 */
@Slf4j
@Service
public class LoginStatusService {
    @Resource
    private LoginStatusMapper loginStatusMapper;
    @Resource
    private RedisTemplate redisTemplate;


    public PageInfo selectLoginStatus(int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        List<LoginStatus> loginStatusList = this.loginStatusMapper.selectAll();
        return new PageInfo(loginStatusList);
    }


}
