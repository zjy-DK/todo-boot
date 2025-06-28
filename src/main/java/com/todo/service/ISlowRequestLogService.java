package com.todo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.todo.domain.SlowRequestLog;
import com.todo.domain.vo.PageQuery;

/**
 * @author zjy
 * @date 2024/12/13  10:33
 */
public interface ISlowRequestLogService extends IService<SlowRequestLog> {

    /**
     * 分页获取慢请求日志
     */
    Page<SlowRequestLog> getPage(PageQuery<SlowRequestLog> pageQuery);

    /**
     * 新增慢请求日志
     */
    void addLog(SlowRequestLog requestLog);
}
