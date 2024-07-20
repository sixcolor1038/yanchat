package com.yan.yanchat.common.user.domain.vo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @Author: sixcolor
 * @Date: 2024-02-16
 * @Description:
 */
@Data
public class ModifyNameReq {


    @ApiModelProperty(value = "用户名称")
    @NotBlank(message = "用户名称不能为空")
    @Length(max = 6, message = "用户名称不能超过六位")
    private String name;
}
