package com.yan.yanchat.common.websocket.domain.vo.req;

import lombok.Data;

/**
 * @Author: sixcolor
 * @Date: 2024-02-14 10:33
 * @Description:
 */
@Data
public class WSBaseReq {
    /**
     * @see com.yan.yanchat.common.websocket.domain.enums.WSReqTypeEnum
     */
    private Integer type;

    private String data;
}
