package com.yan.yanchat.common.user.domain.vo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @Author: sixcolor
 * @Date: 2024-02-16 16:07
 * @Description:
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BlackReq {


    @NotNull
    @ApiModelProperty("拉黑用户的uid")
    private Long uid;
}
