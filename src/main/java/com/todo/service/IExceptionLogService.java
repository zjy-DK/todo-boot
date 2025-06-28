package com.todo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.todo.domain.ExceptionLog;

/**
 * @author zjy
 * @date 2024/12/14  17:16
 */
public interface IExceptionLogService extends IService<ExceptionLog> {

    /**
     * 新增异常日志
     */
    void addLog(ExceptionLog sysExceptionLog);

}
