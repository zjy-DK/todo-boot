package com.todo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.todo.domain.User;
import com.todo.domain.vo.PageQuery;
import com.todo.domain.vo.UpdatePasswordReq;
import com.todo.domain.vo.UpdateUserReq;

/**
 * @author zjy
 * @date 2024/12/08  19:24
 */
public interface IUserService extends IService<User> {

    /**
     * 获取用户分页
     */
    Page<User> getPage(PageQuery<User> pageQuery);

    /**
     * 修改用户信息
     */
    void updateUser(UpdateUserReq req);

    /**
     * 修改密码
     */
    void updatePassword(UpdatePasswordReq req);
}
