package com.todo.config.idempotent;

import cn.hutool.crypto.digest.MD5;
import com.todo.base.BaseConstant;
import com.todo.util.RedisLock;
import com.todo.util.RedisUtil;
import com.todo.util.UUIDUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * 接口幂等性拦截器
 *
 * @author zjy
 * @date 2025/02/13  19:53
 */
@Slf4j
@Component
public class IdempotentInterceptor implements HandlerInterceptor {

    @Resource
    private RedisUtil redisUtil;
    @Resource
    private RedisLock redisLock;

    private static final String IDEMPOTENT_HEADER = "Idempotent";

    private static final String IDEMPOTENT_RESPONSE_DATA = "{\n" +
            "    \"code\": 2002,\n" +
            "    \"message\": \"请勿重复提交\",\n" +
            "    \"data\": null\n" +
            "}";

    /**
     * token作为头，接口地址+请求参数MD5存入redis，如果redis有代表重复请求
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!request.getMethod().equals(BaseConstant.POST)) {
            return true;
        }
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        //获取方法是否有幂等性注解
        Idempotent methodAnnotation = handlerMethod.getMethodAnnotation(Idempotent.class);
        if (methodAnnotation == null) {
            return true;
        }
        int expiredTime = methodAnnotation.expireTime();
        String token = request.getHeader(BaseConstant.TOKEN_HEADER);
        String requestUri = request.getRequestURI();
        String body = getPostData(request);
        String md5 = MD5.create().digestHex(requestUri + body);
        String key = String.format("%s:%s:%s", IDEMPOTENT_HEADER, token, md5);
        if (methodAnnotation.distributedLock()) {
            boolean lock = redisLock.tryLock(key, UUIDUtils.nextUuid(), 1);
            if (!lock) {
                log.warn("幂等性校验不通过，uri:" + requestUri + ",token:" + token);
                response.setStatus(HttpServletResponse.SC_OK);
                response.getOutputStream().write(IDEMPOTENT_RESPONSE_DATA.getBytes(StandardCharsets.UTF_8));
                return false;
            }
        }
        if (redisUtil.hasKey(key)) {
            log.warn("幂等性校验不通过，uri:" + requestUri + ",token:" + token);
            response.setStatus(HttpServletResponse.SC_OK);
            response.getOutputStream().write(IDEMPOTENT_RESPONSE_DATA.getBytes(StandardCharsets.UTF_8));
            return false;
        } else {
            redisUtil.setCacheObject(key, "", (long) expiredTime, TimeUnit.SECONDS);
        }
        return true;
    }

    /**
     * 读取body数据
     */
    private String getPostData(HttpServletRequest request) {
        StringBuilder data = new StringBuilder();
        String line;
        BufferedReader reader;
        try {
            reader = request.getReader();
            while (null != (line = reader.readLine())) {
                data.append(line);
            }
        } catch (IOException e) {
            return null;
        }
        return data.toString();
    }
}
