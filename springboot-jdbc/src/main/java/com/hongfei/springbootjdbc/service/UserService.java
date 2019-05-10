package com.hongfei.springbootjdbc.service;

import com.hongfei.springbootjdbc.dao.UserDao;
import com.hongfei.springbootjdbc.model.User;
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
    private UserDao userDao;

    @Override
    public List<User> selectAll() {
        return this.userDao.selectAll();
    }

    @Override
    public User selectById(Long id) {
        return this.userDao.selectById(id);
    }

    @Override
    public void insert(User user) {
        this.userDao.insert(user);
    }

    @Override
    public void update(User user) {
        this.userDao.update(user);
    }

    @Override
    public int delete(Long id) {
        return this.userDao.delete(id);
    }
}
