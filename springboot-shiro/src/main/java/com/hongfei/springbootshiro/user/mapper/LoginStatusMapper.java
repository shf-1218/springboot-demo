package com.hongfei.springbootshiro.user.mapper;

import com.hongfei.springbootshiro.user.model.LoginStatus;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoginStatusMapper {

    LoginStatus selectByUserIdAndPlatform(@Param("userId") Long userId, @Param("platform") int platform);

    void update(LoginStatus loginStatus);

    void insert(LoginStatus loginStatus);

    List<LoginStatus> selectAll();

    List<LoginStatus> selectByUserId(@Param("userId") Long userId);
}