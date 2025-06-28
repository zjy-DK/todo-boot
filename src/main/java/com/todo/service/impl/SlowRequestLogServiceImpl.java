package com.todo.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.todo.domain.SlowRequestLog;
import com.todo.domain.vo.PageQuery;
import com.todo.mapper.SlowRequestLogMapper;
import com.todo.service.ISlowRequestLogService;
import com.todo.util.MailUtils;
import com.todo.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author zjy
 * @date 2024/12/13  10:33
 */
@Slf4j
@Service
public class SlowRequestLogServiceImpl extends ServiceImpl<SlowRequestLogMapper, SlowRequestLog>
        implements ISlowRequestLogService {

    @Value("${warningNotice.exceptionNotice}")
    public Boolean exceptionNotice;
    @Value("${warningNotice.receiver}")
    public String receiver;

    @Resource
    private ThreadPoolExecutor threadPoolExecutor;
    @Resource
    private MailUtils mailUtils;

    /**
     * 分页获取慢请求日志
     */
    @Override
    public Page<SlowRequestLog> getPage(PageQuery<SlowRequestLog> pageQuery) {
        LambdaQueryWrapper<SlowRequestLog> queryWrapper = new LambdaQueryWrapper<>();
        if (pageQuery.getQuery() != null) {
            SlowRequestLog query = pageQuery.getQuery();
            queryWrapper.eq(StringUtils.isNotEmpty(query.getIp()), SlowRequestLog::getIp, query.getIp())
                    .like(StringUtils.isNotEmpty(query.getRequestPath()), SlowRequestLog::getRequestPath,
                            query.getRequestPath())
                    .like(StringUtils.isNotEmpty(query.getRequestBody()), SlowRequestLog::getRequestBody,
                            query.getRequestBody())
                    .eq(!Objects.isNull(query.getReadFlag()), SlowRequestLog::getReadFlag,
                            query.getReadFlag());
        }
        queryWrapper.select(SlowRequestLog::getId, SlowRequestLog::getRequestPath, SlowRequestLog::getRequestType,
                        SlowRequestLog::getIp, SlowRequestLog::getRequestTime, SlowRequestLog::getExecuteTime,
                        SlowRequestLog::getReadFlag)
                .orderByDesc(SlowRequestLog::getRequestTime);
        return page(new Page<>(pageQuery.getCurrentPage(), pageQuery.getPageSize()), queryWrapper);
    }

    /**
     * 新增网关日志
     */
    @Override
    public void addLog(SlowRequestLog requestLog) {
        save(requestLog);
        if (exceptionNotice && StringUtils.isNotEmpty(receiver)) {
            //发送预警邮件
            threadPoolExecutor.execute(() -> {
                String createTimeStr = DateUtil.format(requestLog.getRequestTime(), "yyyy-MM-dd HH:mm:ss");
                String content = "系统于" + createTimeStr + "出现慢请求，请及时查看。接口：" + requestLog.getRequestPath() +
                        "，请求耗时：" + requestLog.getExecuteTime() + "毫秒";
                mailUtils.send("todo-boot", receiver, "系统慢请求预警", content, false, null,
                        null, null);
            });
        }
    }
}
