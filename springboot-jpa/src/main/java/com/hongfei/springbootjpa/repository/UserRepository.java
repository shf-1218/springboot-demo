package com.hongfei.springbootjpa.repository;

import com.hongfei.springbootjpa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: springboot-demo
 * @Date: 2019-04-20 12:43
 * @Author: Mr.Shen
 * @Description: 用户访问数据库接口
 */
@Mapper
public interface UserRepository extends JpaRepository<User,Long> {

    List<User> findAllByUsername(String username);
}
