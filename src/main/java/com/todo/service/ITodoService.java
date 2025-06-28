package com.todo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.todo.domain.Todo;

/**
 * @author zjy
 * @date 2025/01/15  16:00
 */
public interface ITodoService extends IService<Todo> {

    /**
     * 添加备忘录
     */
    Integer addTodo(Todo todo);

    /**
     * 更新备忘录
     */
    void updateTodo(Todo todo);

    /**
     * 完成备忘录
     */
    void completeTodo(Integer id);
}
