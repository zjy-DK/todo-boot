package com.todo.config.filter;

import com.alibaba.fastjson2.JSONObject;
import com.todo.base.BaseConstant;
import com.todo.domain.SlowRequestLog;
import com.todo.service.ISlowRequestLogService;
import com.todo.util.IpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Date;
import java.util.Enumeration;
import java.util.concurrent.ThreadPoolExecutor;

import static com.todo.base.BaseConstant.SLOW_REQUEST_TIME;

/**
 * 请求日志
 *
 * @author zjy
 * @date 2025/05/28  16:23
 */
@Slf4j
@Component
public class RequestLogInterceptor implements HandlerInterceptor {

    @Resource
    private ISlowRequestLogService slowRequestLogService;
    @Resource
    private ThreadPoolExecutor threadPoolExecutor;

    private static final String REQ_LOG_KEY = "_reqLog";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        SlowRequestLog requestLog = new SlowRequestLog();
        requestLog.setRequestTime(new Date());
        request.setAttribute(REQ_LOG_KEY, requestLog);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        Object obj = request.getAttribute(REQ_LOG_KEY);
        if (obj instanceof SlowRequestLog) {
            SlowRequestLog requestLog = (SlowRequestLog) obj;
            requestLog.setRequestType(request.getMethod());
            requestLog.setRequestPath(request.getRequestURI());
            requestLog.setIp(IpUtils.getIpAddress(request));
            requestLog.setReadFlag(false);
            requestLog.setRequestContentType(request.getContentType());
            requestLog.setRequestBody(getBody(request));
            requestLog.setRequestParam(request.getQueryString());
            requestLog.setResponseTime(new Date());
            requestLog.setExecuteTime(requestLog.getResponseTime().getTime() - requestLog.getRequestTime().getTime());
            if (requestLog.getExecuteTime() > SLOW_REQUEST_TIME) {
                threadPoolExecutor.execute(() -> {
                    slowRequestLogService.addLog(requestLog);
                });
            }
        }
    }

    /**
     * 构建请求头JSON数据
     */
    private String buildRequestHeaderJson(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        JSONObject jsonObject = new JSONObject();
        final String cookie = "cookie";
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            if (!headerName.equals(BaseConstant.TOKEN_HEADER) && !headerName.equals(cookie)) {
                jsonObject.put(headerName, headerValue);
            }
        }
        return jsonObject.toJSONString();
    }

    /**
     * 获取请求体数据
     */
    private String getBody(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return sb.toString();
    }
}
