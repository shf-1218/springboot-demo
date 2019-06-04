package com.hongfei.springbootshiro.common.model;

import com.github.pagehelper.Page;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author 申鸿飞
 * @create 2017-11-09 17:17
 **/
public class PageInfo<T> implements Serializable {
    private Integer page;//当前页码
    private Integer pageSize;//分页大小
    private Integer totalNumber;//总行数
    private Integer totalPage;//	总页数
    private List<T> data;

    public PageInfo(List<T> list) {
        if (list instanceof Page) {
            Page<T> page = (Page<T>) list;
            this.page = page.getPageNum();
            this.pageSize = page.getPageSize();
            this.totalNumber = (int) page.getTotal();
            this.totalPage = page.getPages();
            this.data = Collections.unmodifiableList(list);
        } else if (list instanceof Collection) {
            this.page = 1;
            this.pageSize = list.size();
            this.totalPage = this.pageSize > 0 ? 1 : 0;
            this.totalNumber = list.size();
            this.data = Collections.unmodifiableList(list);
        }
    }

    public PageInfo(List<T> list, Integer page, Integer pageSize, Integer totalNumber) {
        if (list instanceof Collection) {
            this.page = page;
            this.pageSize = pageSize;
            this.totalNumber = totalNumber;
            this.totalPage = totalNumber % this.pageSize > 0 ? totalNumber / this.pageSize + 1 : totalNumber / this
                    .pageSize;
            this.data = Collections.unmodifiableList(list);
        }
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(Integer totalNumber) {
        this.totalNumber = totalNumber;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public boolean getFlag() {
        if (this.page == this.totalPage) {
            return true;
        } else {
            return false;
        }
    }
}
