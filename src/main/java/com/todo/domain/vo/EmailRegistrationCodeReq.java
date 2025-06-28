package com.todo.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author zjy
 * @date 2025/06/03  10:41
 */
@Data
public class EmailRegistrationCodeReq implements Serializable {

    @Schema(description = "邮箱")
    @NotNull(message = "邮箱不能为空")
    private String email;
}
