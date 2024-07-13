package com.yan.yanchat.common.websocket.domain.vo.resp.ws;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.Date;

/**
 * @Author: sixcolor
 * @Date: 2024-02-14 12:46
 * @Description:
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WSMemberChange {
    public static final Integer CHANGE_TYPE_ADD = 1;
    public static final Integer CHANGE_TYPE_REMOVE = 2;
    @ApiModelProperty("群组id")
    private Long roomId;
    @ApiModelProperty("变动uid集合")
    private Long uid;
    @ApiModelProperty("变动类型 1加入群组 2移除群组")
    private Integer changeType;
    @ApiModelProperty("在线状态 1在线 2离线")
    private Integer activeStatus;
    @ApiModelProperty("最后一次上下线时间")
    private Date lastOptTime;
}
