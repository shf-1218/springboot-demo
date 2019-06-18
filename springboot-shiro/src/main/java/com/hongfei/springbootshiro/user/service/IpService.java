package com.hongfei.springbootshiro.user.service;

import com.github.pagehelper.PageHelper;
import com.hongfei.springbootshiro.common.model.PageInfo;
import com.hongfei.springbootshiro.common.model.ResponseCode;
import com.hongfei.springbootshiro.common.model.Result;
import com.hongfei.springbootshiro.user.common.Constant;
import com.hongfei.springbootshiro.user.mapper.IpForbiddenMapper;
import com.hongfei.springbootshiro.user.model.IpForbidden;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author hongfei.shen
 * @date 2019/06/18
 */
@Slf4j
@Service
public class IpService {
    @Resource
    private IpForbiddenMapper ipForbiddenMapper;
    @Resource
    private RedisTemplate redisTemplate;


    public PageInfo selectAll(int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        List<IpForbidden> ipForbiddenList = ipForbiddenMapper.selectAll();
        return new PageInfo(ipForbiddenList);
    }

    public Result insert(IpForbidden ipForbidden) {
        if (this.isExistIp(ipForbidden.getId(), ipForbidden.getIp())) {
            return Result.error(ResponseCode.name_already_exist.getMessage());
        }
        ipForbiddenMapper.insert(ipForbidden);
        return Result.success(ipForbidden);
    }

    private boolean isExistIp(Long id, String ip) {
        return this.ipForbiddenMapper.isExistIp(id, ip);
    }

    public Result update(IpForbidden ipForbidden) {
        IpForbidden oldIpForbidden = this.selectById(ipForbidden.getId());
        if (oldIpForbidden == null) {
            return Result.error(ResponseCode.data_not_exist.getMessage());
        }
        if (this.isExistIp(ipForbidden.getId(), ipForbidden.getIp())) {
            return Result.error(ResponseCode.name_already_exist.getMessage());
        }
        ipForbiddenMapper.update(ipForbidden);
        return Result.success(ipForbidden);
    }

    public Result delete(Long id) {
        IpForbidden oldIpForbidden = this.selectById(id);
        if (oldIpForbidden == null) {
            return Result.error(ResponseCode.data_not_exist.getMessage());
        }
        this.ipForbiddenMapper.updateStatus(id, Constant.STATUS_DELETE);
        return Result.success(id);
    }

    public Result getById(Long id) {
        IpForbidden ipForbidden = this.selectById(id);
        return Result.success(ipForbidden);
    }

    public IpForbidden selectById(Long id) {
        return this.ipForbiddenMapper.selectById(id);

    }
    public boolean isForbiddenIp(String remoteAddr) {
        Boolean result = redisTemplate.opsForSet().isMember("ip_intercepter", remoteAddr);
        log.debug("isForbiddenIp result : {}", result);
        return result;
    }


}
