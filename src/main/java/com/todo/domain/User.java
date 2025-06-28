package com.todo.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.todo.base.BaseEntity;
import com.todo.base.BaseStatusEnum;
import com.todo.domain.enums.SexEnum;
import lombok.Data;

import java.util.Date;

/**
 * 用户对象
 *
 * @author ruoyi
 */
@Data
@TableName("user")
public class User extends BaseEntity {

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 手机号码
     */
    private String phoneNumber;

    /**
     * 用户性别
     */
    private SexEnum sex;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 密码
     */
    private String password;

    /**
     * 帐号状态（0正常 1停用）
     */
    private BaseStatusEnum status;

    /**
     * 最后登录IP
     */
    private String loginIp;

    /**
     * 最后登录时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date loginDate;
}
