package com.yan.yanchat.common.websocket.domain.vo.resp;

import lombok.Data;

/**
 * @Author: sixcolor
 * @Date: 2024-02-14 10:42
 * @Description:
 */
@Data
public class WSBaseResp<T> {
    /**
     * @see com.yan.yanchat.common.websocket.domain.enums.WSRespTypeEnum
     */
    private Integer type;
    private T data;
}
