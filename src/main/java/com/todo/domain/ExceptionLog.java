package com.todo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统异常日志
 *
 * @author zjy
 * @date 2024/12/14  17:12
 */
@Data
@TableName("exception_log")
public class ExceptionLog implements Serializable {

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 异常类型
     */
    private String type;

    /**
     * 异常信息
     */
    private String message;

    /**
     * 异常栈信息
     */
    private String stackLog;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 是否已读
     */
    private Boolean readFlag;
}
