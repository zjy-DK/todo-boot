package com.todo.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.todo.config.idempotent.Idempotent;
import com.todo.domain.Todo;
import com.todo.domain.enums.TodoStatusEnum;
import com.todo.domain.vo.HomeTodoListResp;
import com.todo.domain.vo.OperationById;
import com.todo.domain.vo.PageQuery;
import com.todo.service.ITodoService;
import com.todo.util.AssertUtil;
import com.todo.util.StringUtils;
import com.todo.util.UserUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author zjy
 * @date 2025/01/15  16:00
 */
@Slf4j
@RestController
@RequestMapping("/todo/")
@Tag(name = "todo功能", description = "todo功能")
public class TodoController{

    @Resource
    private ITodoService todoService;
    @Resource
    private UserUtils userUtils;

    @GetMapping("homeTodoList")
    @Operation(summary = "获取首页todo列表", description = "获取首页todo列表")
    public HomeTodoListResp homeTodoList(@RequestParam("top") Boolean top) {
        HomeTodoListResp resp = new HomeTodoListResp();
        List<Todo> todos = todoService.list(new LambdaQueryWrapper<Todo>()
                .eq(Todo::getUserId, userUtils.getLoginUserId())
                .eq(Todo::getStatus, TodoStatusEnum.normal)
                .orderByAsc(Todo::getPriority));
        if (top != null && top) {
            resp.setTopTodoList(todos.stream().filter(Todo::getTop).collect(Collectors.toList()));
        }
        resp.setUnCompleteTodoList(todos.stream().filter(x -> !x.getTop()).collect(Collectors.toList()));
        return resp;
    }

    @PostMapping("getTodoPage")
    @Operation(summary = "获取todo分页", description = "获取todo分页")
    public Page<Todo> getTodoPage(@Valid @RequestBody PageQuery<Todo> pageQuery) {
        Todo query = pageQuery.getQuery();
        String sort = pageQuery.getSort();
        LambdaQueryWrapper<Todo> wrapper = new LambdaQueryWrapper<Todo>().eq(
                Todo::getUserId, userUtils.getLoginUserId());
        if (StringUtils.isNotEmpty(query.getTitle())) {
            wrapper.like(Todo::getTitle, query.getTitle())
                    .orderByDesc(Todo::getCreateTime);
        } else if (query.getStatus() != null) {
            wrapper.eq(Todo::getStatus, query.getStatus())
                    .orderByDesc(Todo::getCreateTime);
        } else if (StringUtils.isEmpty(sort)) {
            if ("priority".equals(sort)) {
                wrapper.orderByAsc(Todo::getPriority, Todo::getCreateTime);
            } else if ("createTime".equals(sort)) {
                wrapper.orderByDesc(Todo::getCreateTime);
            }
        }
        return todoService.page(new Page<>(pageQuery.getCurrentPage(), pageQuery.getPageSize()), wrapper);
    }

    @GetMapping("getTodoById")
    @Operation(summary = "根据ID获取todo", description = "根据ID获取todo")
    public Todo getTodoById(@NotNull(message = "id不能为空") @RequestParam("id") String id) {
        return todoService.getById(id);
    }

    @Idempotent()
    @PostMapping("addTodo")
    @Operation(summary = "添加todo", description = "添加todo")
    public Integer addTodo(@RequestBody Todo todo) {
        AssertUtil.isNull(todo, "备忘录不能为空");
        AssertUtil.isNull(todo.getTitle(), "备忘录标题不能为空");
        return todoService.addTodo(todo);
    }

    @Idempotent()
    @PostMapping("updateTodo")
    @Operation(summary = "更新todo", description = "更新todo")
    public void updateTodo(@RequestBody Todo todo) {
        AssertUtil.isNull(todo, "备忘录不能为空");
        AssertUtil.isNull(todo.getId(), "备忘录ID不能为空");
        todoService.updateTodo(todo);
    }

    @PostMapping("completeTodo")
    @Operation(summary = "完成todo", description = "完成todo")
    public void completeTodo(@Valid @RequestBody OperationById operation) {
        todoService.completeTodo(operation.getId());
    }

    @PostMapping("deleteTodo")
    @Operation(summary = "删除todo", description = "删除todo")
    public void deleteTodo(@Valid @RequestBody OperationById operation) {
        todoService.removeById(operation.getId());
    }
}
