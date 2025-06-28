package com.todo.config.result;

import lombok.Getter;

/**
 * @author zjy
 * @date 2024/12/08  14:57
 */
@Getter
public enum ResultCodeEnum {

    /**
     * 返回结果枚举
     */
    SUCCESS(2001, "成功"),
    COMMON_FAILED(2002, "失败");

    ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    private Integer code;
    private String message;
}
