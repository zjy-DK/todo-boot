package com.todo.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.todo.config.idempotent.Idempotent;
import com.todo.domain.User;
import com.todo.domain.vo.PageQuery;
import com.todo.domain.vo.UpdatePasswordReq;
import com.todo.domain.vo.UpdateUserReq;
import com.todo.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author zjy
 * @date 2024/12/08  19:26
 */
@Slf4j
@RestController
@RequestMapping("/user/")
@Tag(name = "用户功能", description = "用户功能")
public class UserController{

    @Resource
    private IUserService userService;

    @PostMapping("getPage")
    @Operation(summary = "获取用户分页", description = "获取用户分页")
    public Page<User> getPage(@Valid @RequestBody PageQuery<User> pageQuery) {
        return userService.getPage(pageQuery);
    }

    @GetMapping("getById")
    @Operation(summary = "根据id获取用户", description = "根据id获取用户")
    public User getById(@NotNull(message = "id不能为空") @RequestParam("id") String id) {
        return userService.getById(id);
    }

    @Idempotent()
    @PostMapping("updateUser")
    @Operation(summary = "修改用户信息", description = "修改用户信息")
    public void updateUser(@Valid @RequestBody UpdateUserReq req) {
        userService.updateUser(req);
    }

    @Idempotent()
    @PostMapping("updatePassword")
    @Operation(summary = "修改密码", description = "修改密码")
    public void updatePassword(@Valid @RequestBody UpdatePasswordReq req) {
        userService.updatePassword(req);
    }
}
