package com.todo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.todo.domain.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author zjy
 * @date 2024/12/08  19:23
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
