package com.yan.yanchat.common.infrastructure.event;

import com.yan.yanchat.common.chat.domain.dto.ChatMsgRecallDTO;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @Author: sixcolor
 * @Date: 2024-03-29
 * @Description: 消息撤回事件
 */
@Getter
public class MessageRecallEvent extends ApplicationEvent {

    private final ChatMsgRecallDTO recallDTO;

    public MessageRecallEvent(Object source, ChatMsgRecallDTO recallDTO) {
        super(source);
        this.recallDTO = recallDTO;
    }

}
