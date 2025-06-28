package com.todo.config.filter;


import com.todo.base.BaseConstant;
import com.todo.util.RedisUtil;
import com.todo.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zjy
 * @date 2024/12/11  10:27
 */
@Slf4j
@Order(10)
@Component
public class AuthFilter implements Filter {

    @Resource
    private RedisUtil redisUtil;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String urlPath = httpRequest.getRequestURI();

        //swagger 放行
        if (urlPath.contains("/swagger") || urlPath.contains("/v3/api-docs")) {
            chain.doFilter(request, response);
            return;
        }

        // 登录路径放行
        if (urlPath.contains("/auth/")) {
            chain.doFilter(request, response);
            return;
        }

        // 获取 Token
        String token = httpRequest.getHeader(BaseConstant.TOKEN_HEADER);
        if (StringUtils.isEmpty(token)) {
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "请先登录");
            return;
        }

        // 校验 Token 是否存在 Redis 中
        if (!redisUtil.hasKey(BaseConstant.LOGIN_TOKEN + token)) {
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "登录已失效");
            return;
        }

        // 放行请求
        chain.doFilter(request, response);
    }
}
