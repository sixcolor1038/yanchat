package com.yan.yanchat.common.websocket.domain.vo.resp.ws;

import lombok.*;

import java.util.List;

/**
 * @Author: sixcolor
 * @Date: 2024-02-14 12:46
 * @Description:
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WSMsgMark {
    private List<WSMsgMarkItem> markList;
}
