package com.yan.yanchat.common.websocket.domain.vo.resp.ws;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @Author: sixcolor
 * @Date: 2024-02-14 12:46
 * @Description:
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WSFriendApply {
    @ApiModelProperty("申请人")
    private Long uid;
    @ApiModelProperty("申请未读数")
    private Integer unreadCount;
}
