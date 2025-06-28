package com.todo.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.todo.domain.ExceptionLog;
import com.todo.mapper.ExceptionLogMapper;
import com.todo.service.IExceptionLogService;
import com.todo.util.MailUtils;
import com.todo.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author zjy
 * @date 2024/12/14  17:17
 */
@Slf4j
@Service
public class ExceptionLogServiceImpl extends ServiceImpl<ExceptionLogMapper, ExceptionLog>
        implements IExceptionLogService {

    @Value("${warningNotice.exceptionNotice}")
    public Boolean exceptionNotice;
    @Value("${warningNotice.receiver}")
    public String receiver;

    @Resource
    private ThreadPoolExecutor threadPoolExecutor;
    @Resource
    private MailUtils mailUtils;

    /**
     * 新增异常日志
     */
    @Override
    public void addLog(ExceptionLog exceptionLog) {
        exceptionLog.setReadFlag(false);
        save(exceptionLog);
        if (exceptionNotice && StringUtils.isNotEmpty(receiver)) {
            //发送预警邮件
            threadPoolExecutor.execute(() -> {
                String createTimeStr = DateUtil.format(exceptionLog.getCreateTime(), "yyyy-MM-dd HH:mm:ss");
                String content = "系统于" + createTimeStr + "出现异常，请及时查看。异常信息：" + exceptionLog.getMessage();
                mailUtils.send("todo-boot", receiver, "系统异常预警", content, false, null,
                        null, null);
            });
        }
    }
}
