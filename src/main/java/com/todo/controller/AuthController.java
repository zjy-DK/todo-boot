package com.todo.controller;

import com.todo.base.BaseConstant;
import com.todo.domain.User;
import com.todo.domain.vo.EmailRegisterReq;
import com.todo.domain.vo.EmailRegistrationCodeReq;
import com.todo.domain.vo.LoginReq;
import com.todo.domain.vo.LoginResp;
import com.todo.service.IAuthService;
import com.todo.util.UserUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @author zjy
 * @date 2024/12/10  20:39
 */
@Slf4j
@RestController
@RequestMapping("/auth/")
@Tag(name = "认证功能", description = "认证功能")
public class AuthController {

    @Resource
    private IAuthService authService;
    @Resource
    private UserUtils userUtils;

    @PostMapping("emailRegistrationCode")
    @Operation(summary = "获取用户邮箱注册码", description = "获取用户邮箱注册码")
    public void emailRegistrationCode(@Valid @RequestBody EmailRegistrationCodeReq req) {
        authService.emailRegistrationCode(req);
    }

    @PostMapping("emailRegister")
    @Operation(summary = "邮箱注册", description = "用户注册")
    public LoginResp emailRegister(@Valid @RequestBody EmailRegisterReq req) {
        return authService.emailRegister(req);
    }

    @PostMapping("login")
    @Operation(summary = "用户登录", description = "用户登录")
    public LoginResp appLogin(@Valid @RequestBody LoginReq loginReq) {
        return authService.login(loginReq);
    }

    @GetMapping("logout")
    @Operation(summary = "用户登出", description = "用户登出")
    public void logout(HttpServletRequest request) {
        authService.logout(request.getHeader(BaseConstant.TOKEN_HEADER));
    }

    @GetMapping("getLoginUserInfo")
    @Operation(summary = "获取登录用户信息", description = "获取登录用户信息")
    public User getLoginUserInfo() {
        return userUtils.getLoginUser();
    }
}
