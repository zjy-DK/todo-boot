package com.todo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 慢请求日志
 *
 * @author Lion Li
 */

@Data
@TableName("slow_request_log")
public class SlowRequestLog implements Serializable {

    /**
     * 主键 uuid
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 请求路径
     */
    private String requestPath;

    /**
     * 请求类型
     */
    private String requestType;

    /**
     * 请求数据类型
     */
    private String requestContentType;

    /**
     * 请求参数
     */
    private String requestParam;

    /**
     * 请求体
     */
    private String requestBody;

    /**
     * 请求ip
     */
    private String ip;

    /**
     * 请求时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date requestTime;

    /**
     * 响应时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date responseTime;

    /**
     * 执行时间(毫秒)
     */
    private Long executeTime;

    /**
     * 是否已读
     */
    private Boolean readFlag;
}
