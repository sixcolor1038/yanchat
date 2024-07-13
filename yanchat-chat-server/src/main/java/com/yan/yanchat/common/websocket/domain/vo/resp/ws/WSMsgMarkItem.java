package com.yan.yanchat.common.websocket.domain.vo.resp.ws;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: sixcolor
 * @Date: 2024-02-14 12:51
 * @Description:
 */
@Data
public class WSMsgMarkItem {
    @ApiModelProperty("操作者")
    private Long uid;
    @ApiModelProperty("消息id")
    private Long msgId;
    @ApiModelProperty("标记类型 1点赞 2举报")
    private Integer markType;
    @ApiModelProperty("被标记的数量")
    private Integer markCount;
    @ApiModelProperty("动作类型 1确认 2取消")
    private Integer actType;
}
