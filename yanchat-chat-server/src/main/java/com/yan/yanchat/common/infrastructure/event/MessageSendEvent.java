package com.yan.yanchat.common.infrastructure.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @Author: sixcolor
 * @Date: 2024-04-22
 * @Description:
 */
@Getter
public class MessageSendEvent extends ApplicationEvent {
    private Long msgId;

    public MessageSendEvent(Object source, Long msgId) {
        super(source);
        this.msgId = msgId;
    }
}
