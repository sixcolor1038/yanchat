package com.yan.yanchat.common.infrastructure.event;

import com.yan.yanchat.common.user.domain.entity.UserApply;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @Author: sixcolor
 * @Date: 2024-07-29
 * @Description: 好友申请事件
 */
@Getter
public class UserApplyEvent extends ApplicationEvent {

    private UserApply userApply;

    public UserApplyEvent(Object source, UserApply userApply) {
        super(source);
        this.userApply = userApply;
    }

}
