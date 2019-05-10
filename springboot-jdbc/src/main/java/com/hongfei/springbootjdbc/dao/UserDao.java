package com.hongfei.springbootjdbc.dao;

import com.hongfei.springbootjdbc.model.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: springboot-demo
 * @Date: 2019-04-19 21:40
 * @Author: Mr.Shen
 * @Description: 用户dao
 */
@Repository
public class UserDao implements IDao<User> {
    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<User> selectAll() {
        List<User> userList = jdbcTemplate.query("select * from  t_user", new Object[]{}, new BeanPropertyRowMapper<>(User.class));
        return userList;
    }

    @Override
    public User selectById(Long id) {
        return jdbcTemplate.queryForObject("select * from t_user where id=?",new Object[]{id},new BeanPropertyRowMapper<>(User.class));
    }

    @Override
    public void insert(User user) {
        String sql="insert into t_user(username,password) values(?,?)";
        jdbcTemplate.update(sql,user.getUsername(),user.getPassword());
    }

    @Override
    public void update(User user) {
        String sql="update t_user set username=? ,password=? where id=?";
        jdbcTemplate.update(sql,user.getUsername(),user.getPassword(),user.getId());
    }

    @Override
    public int delete(Long id) {
        String sql="Delete from t_user where id=?";
        return jdbcTemplate.update(sql,new Object[]{id});
    }
}
