package com.hongfei.springbootswagger.service;


import com.hongfei.springbootswagger.entity.User;
import com.hongfei.springbootswagger.repository.UserRepository;
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
    private UserRepository userRepository;

    @Override
    public List<User> selectAll() {
        return this.userRepository.findAll();
    }

    @Override
    public User selectById(Long id) {
        return this.userRepository.findById(id).get();
    }

    @Override
    public void insert(User user) {
        this.userRepository.save(user);
    }

    @Override
    public void update(User user) {
        this.userRepository.saveAndFlush(user);
    }

    @Override
    public void delete(Long id) {
         this.userRepository.deleteById(id);
    }

    public List<User> findAllByUserName(String userName) {
       return this.userRepository.findAllByUsername(userName);
    }
}
