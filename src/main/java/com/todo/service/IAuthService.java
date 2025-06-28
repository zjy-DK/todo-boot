package com.todo.service;


import com.todo.domain.User;
import com.todo.domain.vo.EmailRegisterReq;
import com.todo.domain.vo.EmailRegistrationCodeReq;
import com.todo.domain.vo.LoginReq;
import com.todo.domain.vo.LoginResp;

import javax.validation.Valid;

/**
 * @author zjy
 * @date 2024/12/10  20:40
 */
public interface IAuthService {

    /**
     * 用户登录
     */
    LoginResp login(LoginReq loginReq);

    /**
     * 用户登出
     */
    void logout(String token);

    /**
     * 移除登录信息
     */
    void removeLoginInfo(User user);

    /**
     * 获取用户邮箱注册码
     */
    void emailRegistrationCode(@Valid EmailRegistrationCodeReq req);

    /**
     * 邮箱注册
     */
    LoginResp emailRegister(@Valid EmailRegisterReq req);

    void refreshCacheUser(Integer userId);
}
