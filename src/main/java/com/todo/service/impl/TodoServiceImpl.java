package com.todo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.todo.domain.Todo;
import com.todo.domain.enums.TodoPriorityEnum;
import com.todo.domain.enums.TodoStatusEnum;
import com.todo.mapper.TodoMapper;
import com.todo.service.ITodoService;
import com.todo.util.AssertUtil;
import com.todo.util.StringUtils;
import com.todo.util.UserUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Objects;

/**
 * @author zjy
 * @date 2025/01/15  16:01
 */
@Slf4j
@Service
public class TodoServiceImpl extends ServiceImpl<TodoMapper, Todo> implements ITodoService {

    @Resource
    private UserUtils userUtils;

    /**
     * 添加备忘录
     */
    @Override
    public Integer addTodo(Todo todo) {
        todo.setUserId(userUtils.getLoginUserId());
        if (todo.getPriority() == null) {
            todo.setPriority(TodoPriorityEnum.t1);
        }
        if (todo.getTop() == null) {
            todo.setTop(false);
        }
        todo.setStatus(TodoStatusEnum.normal);
        save(todo);
        return todo.getId();
    }

    /**
     * 更新备忘录
     */
    @Override
    public void updateTodo(Todo todo) {
        Todo localTodo = getById(todo.getId());
        AssertUtil.isNull(localTodo, "备忘录不存在");
        Todo updateTodo = new Todo();
        updateTodo.setId(todo.getId());
        updateTodo.setRemark(todo.getRemark());
        if (StringUtils.isNotEmpty(todo.getTitle())) {
            updateTodo.setTitle(todo.getTitle());
        }
        if (!Objects.isNull(todo.getPriority())) {
            updateTodo.setPriority(todo.getPriority());
        }
        if (!Objects.isNull(todo.getTop())){
            updateTodo.setTop(todo.getTop());
        }
        updateById(updateTodo);
    }

    /**
     * 完成备忘录
     */
    @Override
    public void completeTodo(Integer id) {
        Todo localTodo = getById(id);
        AssertUtil.isNull(localTodo, "备忘录不存在");
        Todo updateTodo = new Todo();
        updateTodo.setId(id);
        updateTodo.setStatus(TodoStatusEnum.complete);
        updateTodo.setCompleteTime(new Date());
        updateById(updateTodo);
    }
}
