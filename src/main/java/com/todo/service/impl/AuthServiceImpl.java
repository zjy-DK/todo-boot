package com.todo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.todo.base.BaseConstant;
import com.todo.base.BaseStatusEnum;
import com.todo.base.UserException;
import com.todo.domain.User;
import com.todo.domain.enums.SexEnum;
import com.todo.domain.vo.EmailRegisterReq;
import com.todo.domain.vo.EmailRegistrationCodeReq;
import com.todo.domain.vo.LoginReq;
import com.todo.domain.vo.LoginResp;
import com.todo.mapper.UserMapper;
import com.todo.service.IAuthService;
import com.todo.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author zjy
 * @date 2024/12/10  20:40
 */
@Slf4j
@Service
public class AuthServiceImpl implements IAuthService {

    @Resource
    private HttpServletRequest request;
    @Resource
    private UserMapper userMapper;
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private MailUtils mailUtils;

    /**
     * 用户登录
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public LoginResp login(LoginReq loginReq) {
        //获取用户信息
        User user = userMapper.selectOne(
                new LambdaQueryWrapper<User>().eq(User::getEmail, loginReq.getEmail()));

        //校验
        AssertUtil.isNull(user, "密码错误");
        AssertUtil.equals("用户已停用", user.getStatus(), BaseStatusEnum.stop);
        if (!MD5Utils.passwordIsTrue(loginReq.getPassword(), user.getPassword())) {
            throw new UserException("密码错误");
        }

        //更新用户信息
        user.setLoginIp(IpUtils.getIpAddress(request));
        user.setLoginDate(new Date());
        User updateUser = new User();
        updateUser.setId(user.getId());
        updateUser.setLoginIp(user.getLoginIp());
        updateUser.setLoginDate(user.getLoginDate());
        userMapper.updateById(updateUser);

        //判断该用户是否已登录，登录使用原有token否则生成新的
        String token;
        if (redisUtil.hasKey(BaseConstant.LOGIN_USER + user.getId())) {
            token = redisUtil.getCacheObject(BaseConstant.LOGIN_USER + user.getId());
            //更新缓存中的用户信息
            refreshCacheUser(user.getId());
        } else {
            token = TokenUtils.generateToken(user);
            redisUtil.setCacheObject(BaseConstant.LOGIN_USER + user.getId(), token, BaseConstant.LOGIN_EXPIRE_TIME,
                    TimeUnit.DAYS);
            redisUtil.setCacheObject(BaseConstant.LOGIN_TOKEN + token, user, BaseConstant.LOGIN_EXPIRE_TIME,
                    TimeUnit.DAYS);
        }

        LoginResp resp = new LoginResp();
        resp.setToken(token);
        resp.setUsername(user.getUserName());
        return resp;
    }

    /**
     * 用户登出
     */
    @Override
    public void logout(String token) {
        User user = redisUtil.getCacheObject(BaseConstant.LOGIN_TOKEN + token);
        if (user != null) {
            redisUtil.deleteObject(BaseConstant.LOGIN_USER + user.getId());
            redisUtil.deleteObject(BaseConstant.LOGIN_TOKEN + token);
        }
    }

    /**
     * 移除登录信息
     */
    @Override
    public void removeLoginInfo(User user) {
        AssertUtil.isNull(user.getId(), "移除登录信息失败，id为空");
        String token = redisUtil.getCacheObject(BaseConstant.LOGIN_USER + user.getId());
        logout(token);
    }

    /**
     * 获取用户邮箱注册码
     */
    @Override
    public void emailRegistrationCode(EmailRegistrationCodeReq req) {
        String email = req.getEmail();
        //检查邮箱格式
        if (!email.contains("@")) {
            throw new UserException("邮箱格式不正确");
        }
        //检查邮箱是否存在
        User user = userMapper.selectOne(
                new LambdaQueryWrapper<User>().eq(User::getEmail, email)
                        .select(User::getId, User::getEmail));
        AssertUtil.isNotNull(user, "邮箱已注册");
        //是否存在注册码
        if (redisUtil.hasKey(BaseConstant.EMAIL_REGISTRATION_CODE + email)) {
            throw new UserException("请勿重复生成验证码");
        }
        //生成注册码
        String code = UUIDUtils.nextCode(4);
        redisUtil.setCacheObject(BaseConstant.EMAIL_REGISTRATION_CODE + email, code, 5L, TimeUnit.MINUTES);
        //发送邮件
        String content = String.format("您的邮箱注册码为：%S，有效期五分钟", code);
        mailUtils.send("todo-boot", email, "todo-boot注册验证码码", content, false, null, null,
                null);
    }

    /**
     * 邮箱注册
     */
    @Override
    public LoginResp emailRegister(EmailRegisterReq req) {
        //校验验证码
        if (!redisUtil.hasKey(BaseConstant.EMAIL_REGISTRATION_CODE + req.getEmail())) {
            throw new UserException("验证码已过期");
        }
        String redisCode = redisUtil.getCacheObject(BaseConstant.EMAIL_REGISTRATION_CODE + req.getEmail());
        if (!redisCode.equals(req.getCode())) {
            throw new UserException("验证码错误");
        }
        //生成随机密码
        String password = PasswordUtil.generateStrongPassword(8);
        //模拟前端进行第一次密码加密
        String md5Password = MD5Utils.string2MD5(password.concat(BaseConstant.PASSWORD_SUFFIX));
        //注册默认用户
        User user = new User();
        user.setUserName(req.getEmail());
        user.setEmail(req.getEmail());
        user.setSex(SexEnum.man);
        user.setPassword(MD5Utils.string2MD5(md5Password));
        user.setStatus(BaseStatusEnum.normal);
        user.setLoginDate(new Date());
        userMapper.insert(user);
        //发送密码默认密码到邮件
        String content = String.format("用户已注册成功，默认密码为：%S，为保证账号安全，请尽快前往app修改", password);
        mailUtils.send("todo-boot", req.getEmail(), "todo-boot注册成功", content, false, null,
                null, null);
        //删除验证码
        redisUtil.deleteObject(BaseConstant.EMAIL_REGISTRATION_CODE + req.getEmail());
        //登录用户
        LoginReq loginReq = new LoginReq();
        loginReq.setEmail(user.getEmail());
        loginReq.setPassword(md5Password);
        return login(loginReq);
    }

    /**
     * 刷新缓存中的用户信息
     */
    @Override
    public void refreshCacheUser(Integer userId) {
        String token = redisUtil.getCacheObject(BaseConstant.LOGIN_USER + userId);
        if (redisUtil.hasKey(BaseConstant.LOGIN_TOKEN + token)) {
            User user = userMapper.selectById(userId);
            long expire = redisUtil.getExpire(BaseConstant.LOGIN_TOKEN + token);
            redisUtil.setCacheObject(BaseConstant.LOGIN_TOKEN + token, user, expire, TimeUnit.SECONDS);
        }
    }
}
