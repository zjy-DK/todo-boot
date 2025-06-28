package com.todo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.todo.domain.Todo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author zjy
 * @date 2025/01/15  16:00
 */
@Mapper
public interface TodoMapper extends BaseMapper<Todo> {
}
