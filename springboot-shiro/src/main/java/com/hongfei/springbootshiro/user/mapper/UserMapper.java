package com.hongfei.springbootshiro.user.mapper;

import com.hongfei.springbootshiro.user.model.User;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface UserMapper {
    boolean isExistName(@Param("id") Long id, @Param("name") String name);

    void updateStatus(@Param("id") Long id, @Param("status") int status);

    List<User> selectAllByParam();

    User selectById(@Param("id") Long id);

    int insert(User user);

    int update(User user);

    User selectByLoginName(@Param("userName") String loginName);
}