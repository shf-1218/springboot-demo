package com.hongfei.springbootjpa.service;

import java.util.List;

/**
 * @program: springboot-demo
 * @Date: 2019-04-19 22:11
 * @Author: Mr.Shen
 * @Description: 逻辑层
 */
public interface IService<T> {

    List<T> selectAll();

    T selectById(Long id);

    void insert(T t);

    void update(T t);

    void delete(Long id);
}
