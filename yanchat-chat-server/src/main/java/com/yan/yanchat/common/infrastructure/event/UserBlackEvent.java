package com.yan.yanchat.common.infrastructure.event;

import com.yan.yanchat.common.user.domain.entity.User;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @Author: sixcolor
 * @Date: 2024-02-18 11:14
 * @Description:
 */
@Getter
public class UserBlackEvent extends ApplicationEvent {

    private final User user;

    public UserBlackEvent(Object source, User user) {
        super(source);
        this.user = user;
    }
}
