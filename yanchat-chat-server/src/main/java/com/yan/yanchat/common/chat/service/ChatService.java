package com.yan.yanchat.common.chat.service;

import com.yan.yanchat.common.chat.domain.vo.req.ChatMessageReq;

/**
 * @Author: sixcolor
 * @Date: 2024-03-08
 * @Description: 消息处理类
 */
public interface ChatService {
    /**
     * 发送消息
     * @param request
     * @param uid
     * @return
     */
    Long sendMsg(ChatMessageReq request, Long uid);

}
