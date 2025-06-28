package com.todo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.todo.domain.SlowRequestLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author zjy
 * @date 2024/12/13  10:32
 */
@Mapper
public interface SlowRequestLogMapper extends BaseMapper<SlowRequestLog> {
}
