package com.todo.domain.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author zjy
 * @date 2025/06/03  13:48
 */
@Data
public class UpdatePasswordReq implements Serializable {

    @NotNull(message = "用户id不能为空")
    private Integer id;

    @NotNull(message = "旧密码不能为空")
    private String oldPassword;

    @NotNull(message = "新密码不能为空")
    private String newPassword;
}
