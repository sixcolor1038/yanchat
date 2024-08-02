package com.yan.yanchat.common.infrastructure.event.listener;


import com.yan.yanchat.common.chat.dao.MessageDao;
import com.yan.yanchat.common.chat.domain.entity.Message;
import com.yan.yanchat.common.chat.domain.entity.Room;
import com.yan.yanchat.common.chat.service.cache.RoomCache;
import com.yan.yanchat.common.infrastructure.event.MessageSendEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import javax.validation.constraints.NotNull;


/**
 * @Author: sixcolor
 * @Date: 2024-04-22
 * @Description: 消息发送监听器
 */
@Slf4j
@Component
public class MessageSendListener {

    @Autowired
    private MessageDao messageDao;
    @Autowired
    private RoomCache roomCache;

    @TransactionalEventListener(classes = MessageSendEvent.class, fallbackExecution = true)
    public void handlerMsg(@NotNull MessageSendEvent event) {
        Message message = messageDao.getById(event.getMsgId());
        Room room = roomCache.get(message.getRoomId());
    }

}
