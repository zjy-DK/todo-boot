package com.todo.domain.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

/**
 * @author zjy
 * @date 2025/01/15  15:53
 */
public enum TodoStatusEnum {

    /**
     * 状态
     */
    normal(0, "正常"),
    complete(1, "完成");

    @EnumValue
    private Integer index;
    private String name;

    TodoStatusEnum(Integer index, String name) {
        this.index = index;
        this.name = name;
    }
}
