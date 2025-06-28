package com.todo.domain.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

/**
 * @author zjy
 * @date 2024/12/08  16:02
 */
public enum SexEnum {

    /**
     * 性别
     */
    man(0, "男"),
    woman(1, "女");

    @EnumValue
    private Integer index;
    private String name;

    SexEnum(Integer index, String name) {
        this.index = index;
        this.name = name;
    }
}
