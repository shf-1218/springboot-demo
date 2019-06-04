package com.hongfei.springbootjedis.mapper;

import com.hongfei.springbootjedis.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;


import java.util.List;

/**
 * @program: springboot-demo
 * @Date: 2019-04-20 14:32
 * @Author: Mr.Shen
 * @Description: 用户持久层
 * <p>第一种是基于mybatis3.x版本后提供的注解方式<p/>
 * <p>第二种是早期写法，将SQL写在 XML 中<p/>
 */
@Mapper
public interface IUserMapper extends BaseMapper<User> {

    @Select("select * from t_user ")
    List<User> findAll();

    @Select("select * from t_user where username=#{userName}")
    List<User> findByUserName(@Param("userName") String userName);

    @Insert("insert into t_user(username,password) values(#{username},#{password})")
    int insert(User user);

    @Update("update t_user set username=#{username} ,password=#{password} where id=#{id}")
    void update(User user);

//    void delete(@Param("id") Long id);
//
//    User selectById(@Param("id") Long id);

    int countByUsername(@Param("username") String username);
}
