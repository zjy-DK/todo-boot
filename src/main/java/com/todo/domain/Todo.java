package com.todo.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.todo.base.BaseEntity;
import com.todo.domain.enums.TodoPriorityEnum;
import com.todo.domain.enums.TodoStatusEnum;
import lombok.Data;

import java.util.Date;

/**
 * @author zjy
 * @date 2025/01/15  15:50
 */
@Data
@TableName("todo")
public class Todo extends BaseEntity {

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 标题
     */
    private String title;

    /**
     * 优先级
     */
    private TodoPriorityEnum priority;

    /**
     * 状态
     */
    private TodoStatusEnum status;

    /**
     * 置顶
     */
    private Boolean top;

    /**
     * 完成时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date completeTime;
}
