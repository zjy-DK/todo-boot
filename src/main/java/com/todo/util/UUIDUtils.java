package com.todo.util;

import cn.hutool.core.util.RandomUtil;

import java.util.UUID;

/**
 * @author zjy
 * @date 2024/12/08  20:14
 */
public class UUIDUtils {

    /**
     * 生成UUID
     */
    public static String nextUuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 生成数字验证码
     */
    public static String nextCode(int length) {
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < length; i++) {
            char randomNumber = RandomUtil.randomNumber();
            code.append(randomNumber);
        }
        return String.valueOf(code);
    }

    public static void main(String[] args) {
        String s = nextCode(4);
        System.out.println(s);
    }
}
