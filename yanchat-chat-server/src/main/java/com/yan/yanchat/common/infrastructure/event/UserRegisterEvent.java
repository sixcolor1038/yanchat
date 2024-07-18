package com.yan.yanchat.common.infrastructure.event;

import com.yan.yanchat.common.user.domain.entity.User;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @Author: sixcolor
 * @Date: 2024-02-18 10:23
 * @Description: 用户注册事件
 */
@Getter
public class UserRegisterEvent extends ApplicationEvent {

    private final User user;

    public UserRegisterEvent(Object source, User user) {
        super(source);
        this.user = user;
    }
}
