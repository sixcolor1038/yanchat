package com.yan.yanchat.common.websocket.domain.vo.resp.ws;

import lombok.*;

import java.util.ArrayList;
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
public class WSOnlineOfflineNotify {
    private List<ChatMemberResp> changeList = new ArrayList<>();//新的上下线用户
    private Long onlineNum;//在线人数
}
