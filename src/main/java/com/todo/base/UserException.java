package com.todo.base;

/**
 * 用户友好异常
 *
 * @author zjy
 * @date 2024/12/08  15:19
 */
public class UserException extends RuntimeException {

    public UserException(String message) {
        super(message);
    }
}
