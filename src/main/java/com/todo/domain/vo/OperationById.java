package com.todo.domain.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**操作
 * @author zjy
 * @date 2024/12/14  16:58
 */
@Data
public class OperationById implements Serializable {

    @NotNull(message = "id不能为空")
    private Integer id;
}
