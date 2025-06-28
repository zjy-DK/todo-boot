package com.todo.domain.vo;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 分页查询实体类
 *
 * @author Lion Li
 */
public class PageQuery<T> implements Serializable {

    /**
     * 当前页数
     */
    @NotNull(message = "当前页数不能为空")
    private Integer currentPage;

    /**
     * 分页大小
     */
    @NotNull(message = "分页大小不能为空")
    private Integer pageSize;

    /**
     * 查询条件
     */
    private T query;

    /**
     * 排序条件
     */
    private String sort;

    public PageQuery() {
    }

    public PageQuery(Integer currentPage, Integer pageSize, T query) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.query = query;
    }

    public PageQuery(Integer currentPage, Integer pageSize, T query, String sort) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.query = query;
        this.sort = sort;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public T getQuery() {
        return query;
    }

    public void setQuery(T query) {
        this.query = query;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}
