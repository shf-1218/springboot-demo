package com.hongfei.springbootjedis.service;


import com.hongfei.springbootjedis.mapper.IUserMapper;
import com.hongfei.springbootjedis.model.User;
import com.hongfei.springbootjedis.utils.JedisUtil;
import com.hongfei.springbootjedis.utils.RedisUtil;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: springboot-demo
 * @Date: 2019-04-19 22:13
 * @Author: Mr.Shen
 * @Description: 用户逻辑层
 */
@Service
public class UserService implements IService<User> {
    @Resource
    RedisUtil redisUtil;
    @Resource
    JedisUtil jedisUtil;
    @Resource
    private IUserMapper userMapper;

    @Override
    @Cacheable(value = "userAll",keyGenerator = "keyGenerator",unless = "#result==null||#result.size()==0")
    public List<User> selectAll() {
        return this.userMapper.findAll();
    }

    @Override
    public User selectById(Long id) {
        User user = this.userMapper.selectByPrimaryKey(id);
        redisUtil.set(String.valueOf(id),user);
        return user;
    }

    @Override
    public void insert(User user) {
        this.userMapper.insert(user);
    }

    @Override
    public void update(User user) {
        this.userMapper.update(user);
    }

    @Override
    public void delete(Long id) {
        this.userMapper.deleteByPrimaryKey(id);
    }

    public int countByUsername(String username) {
        int count = this.userMapper.countByUsername(username);
        jedisUtil.set(username,String.valueOf(count),1);
        return count;
    }
}
