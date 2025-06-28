package com.todo.base;

import com.baomidou.mybatisplus.annotation.EnumValue;

/**
 * @author zjy
 * @date 2024/12/08  16:04
 */
public enum BaseStatusEnum {

    /**
     * 状态
     */
    normal(0,"正常"),
    stop(1,"停用");

    @EnumValue
    private Integer index;
    private String name;

    BaseStatusEnum(Integer index, String name) {
        this.index = index;
        this.name = name;
    }
}
