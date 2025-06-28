package com.todo.domain.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zjy
 * @date 2024/12/10  20:46
 */
@Data
public class LoginResp implements Serializable {

    /**
     * token
     */
    private String token;

    /**
     * 用户名
     */
    private String username;
}
