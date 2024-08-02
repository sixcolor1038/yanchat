package com.yan.yanchat.common.infrastructure.event.listener;

import com.yan.yanchat.common.chat.domain.dto.ChatMsgRecallDTO;
import com.yan.yanchat.common.chat.service.ChatService;
import com.yan.yanchat.common.chat.service.cache.MsgCache;
import com.yan.yanchat.common.infrastructure.event.MessageRecallEvent;
import com.yan.yanchat.common.user.service.impl.PushService;
import com.yan.yanchat.common.websocket.service.WebSocketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * @Author: sixcolor
 * @Date: 2024-03-29
 * @Description: 消息撤回监听器
 */
@Slf4j
@Component
public class MessageRecallListener {
    @Autowired
    private WebSocketService webSocketService;
    @Autowired
    private ChatService chatService;
    @Autowired
    private MsgCache msgCache;
    @Autowired
    private PushService pushService;

    @Async
    @TransactionalEventListener(classes = MessageRecallEvent.class, fallbackExecution = true)
    public void evictMsg(MessageRecallEvent event) {
        ChatMsgRecallDTO recallDTO = event.getRecallDTO();
        msgCache.evictMsg(recallDTO.getMsgId());
    }

    @Async
    @TransactionalEventListener(classes = MessageRecallEvent.class, fallbackExecution = true)
    public void sendToAll(MessageRecallEvent event) {
        //pushService.sendPushMsg(WSAdapter.buildMsgRecall(event.getRecallDTO()));
    }

}
