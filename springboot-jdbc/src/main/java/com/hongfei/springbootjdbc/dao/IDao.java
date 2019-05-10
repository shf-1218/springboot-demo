package com.hongfei.springbootjdbc.dao;

import java.util.List;

/**
 * @program: springboot-demo
 * @Date: 2019-04-19 21:38
 * @Author: Mr.Shen
 * @Description: dao接口类
 */
public interface IDao<T> {

    List<T> selectAll();

    T selectById(Long id);

    void insert(T t);

    void update(T t);

    int delete(Long id);


}
