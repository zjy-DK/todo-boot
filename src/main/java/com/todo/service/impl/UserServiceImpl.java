package com.todo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.todo.base.UserException;
import com.todo.domain.User;
import com.todo.domain.vo.PageQuery;
import com.todo.domain.vo.UpdatePasswordReq;
import com.todo.domain.vo.UpdateUserReq;
import com.todo.mapper.UserMapper;
import com.todo.service.IAuthService;
import com.todo.service.IUserService;
import com.todo.util.AssertUtil;
import com.todo.util.MD5Utils;
import com.todo.util.StringUtils;
import com.todo.util.UserUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author zjy
 * @date 2024/12/08  19:25
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Resource
    private IAuthService authService;
    @Resource
    private UserUtils userUtils;

    /**
     * 获取用户分页
     */
    @Override
    public Page<User> getPage(PageQuery<User> pageQuery) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        if (pageQuery.getQuery() != null) {
            User query = pageQuery.getQuery();
            queryWrapper.like(!StringUtils.isEmpty(query.getUserName()), User::getUserName, query.getUserName())
                    .eq(!StringUtils.isEmpty(query.getPhoneNumber()), User::getPhoneNumber, query.getPhoneNumber())
                    .eq(!Objects.isNull(query.getStatus()), User::getStatus, query.getStatus());
        }
        queryWrapper.orderByDesc(User::getCreateTime);
        return page(new Page<>(pageQuery.getCurrentPage(), pageQuery.getPageSize()), queryWrapper);
    }

    /**
     * 修改用户信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUser(UpdateUserReq req) {
        User localUser = getOne(new LambdaQueryWrapper<User>()
                .eq(User::getId, req.getId())
                .select(User::getId));
        AssertUtil.isNull(localUser, "用户不存在");
        User updateUser = new User();
        updateUser.setId(req.getId());
        updateUser.setUserName(req.getUserName());
        updateUser.setPhoneNumber(req.getPhoneNumber());
        updateUser.setSex(req.getSex());
        updateById(updateUser);
        //刷新缓存中的用户信息
        authService.refreshCacheUser(localUser.getId());
    }

    /**
     * 修改密码
     */
    @Override
    public void updatePassword(UpdatePasswordReq req) {
        AssertUtil.equals(req.getOldPassword(), req.getNewPassword(), "新密码不能与旧密码一致");
        User localUser = getById(req.getId());
        AssertUtil.isNull(localUser, "用户不存在");
        if (!MD5Utils.passwordIsTrue(req.getOldPassword(), localUser.getPassword())) {
            throw new UserException("旧密码错误");
        }
        User updateUser = new User();
        updateUser.setId(req.getId());
        updateUser.setPassword(MD5Utils.string2MD5(req.getNewPassword()));
        updateById(updateUser);
        localUser.setPassword(updateUser.getPassword());
        //移除登录信息
        authService.removeLoginInfo(localUser);
    }
}
