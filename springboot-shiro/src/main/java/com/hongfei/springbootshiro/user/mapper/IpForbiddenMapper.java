package com.hongfei.springbootshiro.user.mapper;

import com.hongfei.springbootshiro.user.model.IpForbidden;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IpForbiddenMapper {
    List<IpForbidden> selectAll();

    boolean isExistIp(@Param("id") Long id, @Param("ip") String ip);

    int insert(IpForbidden ipForbidden);

    void update(IpForbidden ipForbidden);

    IpForbidden selectById(@Param("id") Long id);

    void updateStatus(@Param("id") Long id, @Param("status") int status);
}