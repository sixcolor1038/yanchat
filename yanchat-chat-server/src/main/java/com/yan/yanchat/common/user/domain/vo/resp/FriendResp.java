package com.yan.yanchat.common.user.domain.vo.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: sixcolor
 * @Date: 2024-04-28
 * @Description: 好友校验
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FriendResp {

    @ApiModelProperty("好友uid")
    private Long uid;
    /**
     * @see com.yan.yanchat.common.user.domain.enums.UserActiveStatusEnum
     */
    @ApiModelProperty("在线状态 1在线 2离线")
    private Integer activeStatus;
}
