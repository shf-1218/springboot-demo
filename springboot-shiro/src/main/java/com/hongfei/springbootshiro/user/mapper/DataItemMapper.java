package com.hongfei.springbootshiro.user.mapper;


import com.hongfei.springbootshiro.user.model.DataItem;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DataItemMapper {
    List<DataItem> selectAll();

    boolean isExistName(@Param("id") Long id, @Param("key") String key);

    void insert(DataItem dataItem);

    void update(DataItem dataItem);

    DataItem selectById(@Param("id") Long id);

    void updateStatus(@Param("id") Long id,@Param("status") int status);

    String selectByKey(@Param("key") String key);
}