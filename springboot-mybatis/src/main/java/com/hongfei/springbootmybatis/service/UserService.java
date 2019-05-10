package com.hongfei.springbootmybatis.service;


import com.hongfei.springbootmybatis.mapper.IUserMapper;
import com.hongfei.springbootmybatis.model.User;
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
    private IUserMapper userMapper;

    @Override
    public List<User> selectAll() {
        return this.userMapper.findAll();
    }

    @Override
    public User selectById(Long id) {
        return this.userMapper.selectByPrimaryKey(id);
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
        return this.userMapper.countByUsername(username);

    }
}
