package com.yan.yanchat.common.user.domain.vo.req.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: sixcolor
 * @Date: 2024-07-27
 * @Description: 表情包
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEmojiReq {
    /**
     * 表情地址
     */
    @ApiModelProperty(value = "新增的表情url")
    private String expressionUrl;

}
