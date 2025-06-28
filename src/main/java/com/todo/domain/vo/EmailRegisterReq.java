package com.todo.domain.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author zjy
 * @date 2025/06/03  10:59
 */
@Data
public class EmailRegisterReq implements Serializable {

    /**
     * 用户邮箱
     */
    @NotNull(message = "用户邮箱不能为空")
    private String email;

    /**
     * 验证码
     */
    @NotNull(message = "验证码不能为空")
    private String code;
}
