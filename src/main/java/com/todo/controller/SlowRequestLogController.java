package com.todo.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.todo.domain.SlowRequestLog;
import com.todo.domain.vo.PageQuery;
import com.todo.service.ISlowRequestLogService;
import com.todo.util.AssertUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * 慢请求日志
 *
 * @author zjy
 * @date 2024/12/13  10:34
 */
@Slf4j
@RestController
@RequestMapping("/slowRequestLog/")
@Tag(name = "慢请求日志", description = "慢请求日志")
public class SlowRequestLogController {

    @Resource
    private ISlowRequestLogService slowRequestLogService;

    @PostMapping("page")
    @Operation(summary = "分页获取慢请求日志", description = "分页获取慢请求日志")
    public Page<SlowRequestLog> page(@Valid @RequestBody PageQuery<SlowRequestLog> pageQuery) {
        return slowRequestLogService.getPage(pageQuery);
    }

    @GetMapping("getById")
    @Operation(summary = "获取慢请求日志详情", description = "获取慢请求日志详情")
    public SlowRequestLog getById(@NotNull(message = "id不能为空") @RequestParam("id") String id) {
        SlowRequestLog requestLog = slowRequestLogService.getById(id);
        //第一次查询未读过的日志时改变日志已读状态
        if (requestLog != null && !requestLog.getReadFlag()) {
            SlowRequestLog update = new SlowRequestLog();
            update.setId(requestLog.getId());
            update.setReadFlag(true);
            slowRequestLogService.updateById(update);
        }
        return requestLog;
    }

    @GetMapping("unreadLog")
    @Operation(summary = "获取未读慢请求日志", description = "获取未读慢请求日志")
    public Long unreadLog() {
        return slowRequestLogService.count(new LambdaQueryWrapper<SlowRequestLog>()
                .eq(SlowRequestLog::getReadFlag, false));
    }

    @PostMapping("addLog")
    @Operation(summary = "新增慢请求日志", description = "新增慢请求日志")
    public void addLog(@RequestBody SlowRequestLog requestLog) {
        AssertUtil.isNull(requestLog, "日志对象不能为空");
        slowRequestLogService.addLog(requestLog);
    }

    @PostMapping("clearLog")
    @Operation(summary = "清空慢请求日志", description = "清空慢请求日志")
    public void clearLog() {
        slowRequestLogService.remove(new LambdaQueryWrapper<SlowRequestLog>()
                .isNotNull(SlowRequestLog::getId));
    }
}
