package com.todo.util;

import com.todo.base.BaseConstant;
import com.todo.base.UserException;
import com.todo.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author zjy
 * @date 2025/06/03  09:44
 */
@Slf4j
@Component
public class UserUtils {

    @Resource
    private RedisUtil redisUtil;

    /**
     * 获取当前登录用户
     */
    public User getLoginUser() {
        HttpServletRequest request = getCurrentRequest();
        String token = request.getHeader(BaseConstant.TOKEN_HEADER);
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        return redisUtil.getCacheObject(BaseConstant.LOGIN_TOKEN + token);
    }

    /**
     * 获取当前登录用户ID
     */
    public Integer getLoginUserId() {
        User loginUser = getLoginUser();
        if (loginUser == null) {
            return null;
        }
        return loginUser.getId();
    }

    /**
     * 获取当前登录用户昵称
     */
    public String getLoginUserName() {
        User loginUser = getLoginUser();
        if (loginUser == null) {
            return null;
        }
        return loginUser.getUserName();
    }


    /**
     * 获取当前请求的 HttpServletRequest 对象
     */
    private HttpServletRequest getCurrentRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes) {
            return ((ServletRequestAttributes) requestAttributes).getRequest();
        } else {
            log.error("获取当前请求的 HttpServletRequest 对象失败");
            throw new UserException("获取当前请求的 HttpServletRequest 对象失败");
        }
    }
}
