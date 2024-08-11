package com.yan.yanchat.common.chat.domain.vo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @Author: sixcolor
 * @Date: 2024-02-18
 * @Description: 消息发送请求体
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageReq {
    @NotNull
    @ApiModelProperty("房间id")
    private Long roomId;

    @ApiModelProperty("消息类型")
    @NotNull
    private Integer msgType;

    /**
     * @see com.yan.yanchat.common.chat.domain.entity.msg
     */
    @ApiModelProperty("消息内容，类型不同传值不同")
    @NotNull
    private Object body;

}
