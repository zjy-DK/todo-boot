package com.todo.domain.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author zjy
 * @date 2024/12/10  20:44
 */
@Data
public class LoginReq implements Serializable {

    @NotNull(message = "邮箱不能为空")
    private String email;

    @NotNull(message = "密码不能为空")
    private String password;
}
