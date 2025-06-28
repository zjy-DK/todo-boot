package com.todo.domain.vo;

import com.todo.domain.enums.SexEnum;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author zjy
 * @date 2025/06/03  13:45
 */
@Data
public class UpdateUserReq implements Serializable {

    /**
     * 主键
     */
    @NotNull(message = "id不能为空")
    private Integer id;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 手机号码
     */
    private String phoneNumber;

    /**
     * 用户性别
     */
    private SexEnum sex;
}
