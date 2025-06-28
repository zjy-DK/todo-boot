package com.todo.domain.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

/**
 * @author zjy
 * @date 2025/01/15  15:51
 */
public enum TodoPriorityEnum {

    /**
     * 优先级
     */
    t0(0, "T0"),
    t1(1, "T1"),
    t2(2, "T2");

    @EnumValue
    private Integer index;
    private String name;

    TodoPriorityEnum(Integer index, String name) {
        this.index = index;
        this.name = name;
    }
}
