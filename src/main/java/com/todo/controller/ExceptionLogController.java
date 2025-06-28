package com.todo.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.todo.domain.ExceptionLog;
import com.todo.domain.vo.PageQuery;
import com.todo.service.IExceptionLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author zjy
 * @date 2024/12/14  17:17
 */
@Slf4j
@RestController
@RequestMapping("/exception/")
@Tag(name = "异常日志", description = "异常日志")
public class ExceptionLogController{

    @Resource
    private IExceptionLogService exceptionLogService;

    @PostMapping("getPage")
    @Operation(summary = "获取异常日志分页", description = "获取异常日志分页")
    public Page<ExceptionLog> getPage(@Valid @RequestBody PageQuery<ExceptionLog> pageQuery) {
        return exceptionLogService.page(new Page<>(pageQuery.getCurrentPage(), pageQuery.getPageSize()),
                new LambdaQueryWrapper<ExceptionLog>().select(ExceptionLog::getId,
                                ExceptionLog::getType, ExceptionLog::getCreateTime,
                                ExceptionLog::getReadFlag)
                        .orderByDesc(ExceptionLog::getCreateTime));
    }

    @GetMapping("getById")
    @Operation(summary = "获取异常详情", description = "获取异常详情")
    public ExceptionLog getById(@NotNull(message = "id不能为空") @RequestParam("id") String id) {
        ExceptionLog exceptionLog = exceptionLogService.getById(id);
        //第一次查询未读过的日志时改变日志已读状态
        if (exceptionLog != null && !exceptionLog.getReadFlag()) {
            ExceptionLog update = new ExceptionLog();
            update.setId(exceptionLog.getId());
            update.setReadFlag(true);
            exceptionLogService.updateById(update);
        }
        return exceptionLog;
    }

    @GetMapping("unreadExceptionLog")
    @Operation(summary = "获取未读异常日志", description = "获取未读异常日志")
    public Long unreadExceptionLog() {
        return exceptionLogService.count(new LambdaQueryWrapper<ExceptionLog>()
                .eq(ExceptionLog::getReadFlag, false));
    }

    @PostMapping("addExceptionLog")
    @Operation(summary = "新增异常日志", description = "新增异常日志")
    public void addLog(@RequestBody ExceptionLog exceptionLog) {
        exceptionLogService.addLog(exceptionLog);
    }
}
