package com.hongfei.springbootshiro.user.service;

import com.github.pagehelper.PageHelper;
import com.hongfei.springbootshiro.common.model.PageInfo;
import com.hongfei.springbootshiro.common.model.ResponseCode;
import com.hongfei.springbootshiro.common.model.Result;
import com.hongfei.springbootshiro.user.common.Constant;
import com.hongfei.springbootshiro.user.mapper.DataItemMapper;
import com.hongfei.springbootshiro.user.mapper.IpForbiddenMapper;
import com.hongfei.springbootshiro.user.model.DataItem;
import com.hongfei.springbootshiro.user.model.IpForbidden;
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
public class DataItemService {
    @Resource
    private DataItemMapper dataItemMapper;
    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private IpForbiddenMapper ipForbiddenMapper;

    public PageInfo selectAll(int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        List<DataItem> dataItemList = dataItemMapper.selectAll();
        return new PageInfo(dataItemList);
    }

    public boolean isExistName(Long id, String key) {
        return this.dataItemMapper.isExistName(id, key);
    }

    public Result insert(DataItem dataItem) {
        if (this.isExistName(dataItem.getId(), dataItem.getKeyName())) {
            return Result.error(ResponseCode.name_already_exist.getMessage());
        }
        dataItemMapper.insert(dataItem);
        return Result.success(dataItem);
    }

    public Result update(DataItem dataItem) {
        DataItem oldDataItem = this.selectById(dataItem.getId());
        if (oldDataItem == null) {
            return Result.error(ResponseCode.data_not_exist.getMessage());
        }
        if (this.isExistName(dataItem.getId(), dataItem.getKeyName())) {
            return Result.error(ResponseCode.name_already_exist.getMessage());
        }
        dataItemMapper.update(dataItem);
        return Result.success(dataItem);
    }

    public Result getById(Long id) {
        DataItem dataItem = this.selectById(id);
        return Result.success(dataItem);
    }

    public Result delete(Long id) {
        DataItem oldDataItem = this.selectById(id);
        if (oldDataItem == null) {
            return Result.error(ResponseCode.data_not_exist.getMessage());
        }
        dataItemMapper.updateStatus(id, Constant.STATUS_DELETE);
        return Result.success();
    }

    public DataItem selectById(Long id) {
        return this.dataItemMapper.selectById(id);
    }

    public boolean selectIPForbiddenStatus() {
        DataItem dataItem = this.selectById(4L);
        if (dataItem.getKeyValue().equals("true")) {
            return true;
        }
        return false;
    }

    public void openIpIntercept() {
        //更新字典数据
        DataItem dataItem = new DataItem();
        dataItem.setId(4L);
        dataItem.setKeyValue("true");
        this.dataItemMapper.update(dataItem);
        //删除缓存
        redisTemplate.opsForValue().getOperations().delete("ip_intercepter");
        List<IpForbidden> ipForbiddenList = ipForbiddenMapper.selectAll();
        ipForbiddenList.stream().forEach(e -> {
            long time = System.currentTimeMillis() - e.getExpireTime().getTime();
            log.debug("time:{}", time);
            if (time < 0) {
                redisTemplate.opsForSet().add("ip_intercepter", e.getIp());
            }
        });
    }

    public void closeIpIntercept() {
        //更新字典数据
        DataItem dataItem = new DataItem();
        dataItem.setId(4L);
        dataItem.setKeyValue("false");
        this.dataItemMapper.update(dataItem);
        //删除缓存
        redisTemplate.opsForSet().getOperations().delete("ip_intercepter");
    }

    public String selectDataItemByKey(String key) {
        String value = this.dataItemMapper.selectByKey(key);
        return value;
    }
}
