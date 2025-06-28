package com.todo.domain.vo;

import com.todo.domain.Todo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author zjy
 * @date 2025/01/26  16:15
 */
@Data
public class HomeTodoListResp  implements Serializable {

    /**
     * 置顶代办
     */
    private List<Todo> topTodoList;

    /**
     * 未完成代办
     */
    private List<Todo> unCompleteTodoList;
}
