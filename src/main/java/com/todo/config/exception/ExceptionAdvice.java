package com.todo.config.exception;

import com.todo.base.UserException;
import com.todo.config.result.Result;
import com.todo.domain.ExceptionLog;
import com.todo.service.IExceptionLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.annotation.Resource;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 统一异常处理
 *
 * @author zjy
 * @date 2024/12/08  15:20
 */
@Slf4j
@RestControllerAdvice(basePackages = "com.todo")
public class ExceptionAdvice {

    @Resource
    private IExceptionLogService exceptionLogService;
    @Resource
    private ThreadPoolExecutor threadPoolExecutor;

    /**
     * 捕获所有异常
     */
    @ExceptionHandler({Exception.class})
    public Result<?> handleException(Exception ex) {
        log.error(ex.getMessage(), ex);
        //处理用户友好异常
        if (ex.getClass().equals(UserException.class)) {
            return Result.failed(ex.getMessage());
        }
        //处理校验错误异常
        if (ex instanceof BindException) {
            StringBuilder sb = new StringBuilder();
            BindException bindException = (BindException) ex;
            BindingResult bindingResult = bindException.getBindingResult();
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            for (ObjectError item : allErrors) {
                sb.append(item.getDefaultMessage()).append(',');
            }
            sb.deleteCharAt(sb.length() - 1);
            return Result.failed(String.valueOf(sb));
        }
        //处理Sentinel异常
//        if (ex instanceof UndeclaredThrowableException) {
//            Throwable cause = ex.getCause();
//            //限流
//            if (cause instanceof FlowException) {
//                return Result.failed(BaseConstant.LIMIT_MESSAGE);
//            }
//            //熔断
//            if (cause instanceof DegradeException) {
//                return Result.failed(BaseConstant.BREAKER_MESSAGE);
//            }
//        }
        //其他系统异常记录到日志
        threadPoolExecutor.execute(() -> {
            ExceptionLog exceptionLog = new ExceptionLog();
            exceptionLog.setType(ex.getClass().toString());
            exceptionLog.setMessage(ex.getMessage());
            //获取异常栈信息
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            exceptionLog.setStackLog(sw.toString());
            exceptionLog.setCreateTime(new Date());
            exceptionLogService.addLog(exceptionLog);
        });
        return Result.failed("系统异常，请稍候再试或联系管理员");
    }
}
