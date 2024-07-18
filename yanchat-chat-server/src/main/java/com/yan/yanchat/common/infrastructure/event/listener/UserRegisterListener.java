package com.yan.yanchat.common.infrastructure.event.listener;

import com.yan.yanchat.common.infrastructure.event.UserRegisterEvent;
import com.yan.yanchat.common.user.dao.UserDao;
import com.yan.yanchat.common.user.domain.entity.User;
import com.yan.yanchat.common.user.domain.enums.IdempotentEnum;
import com.yan.yanchat.common.user.domain.enums.ItemEnum;
import com.yan.yanchat.common.user.service.UserBackpackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * @Author: sixcolor
 * @Date: 2024-02-18 10:59
 * @Description: 用户注册监听事件
 */
@Component
public class UserRegisterListener {

    @Autowired
    private UserBackpackService userBackpackService;
    @Autowired
    private UserDao userDao;

    /**
     * 发放改名卡
     * 异步执行
     * 在事务提交后执行，不能影响用户注册的事务
     */
    @Async
    @TransactionalEventListener(classes = UserRegisterEvent.class, phase = TransactionPhase.AFTER_COMMIT)
    public void sendCard(UserRegisterEvent event) {
        User user = event.getUser();
        userBackpackService.acquireItem(user.getId(), ItemEnum.MODIFY_NAME_CARD.getId(), IdempotentEnum.UID, user.getId().toString());
    }

    /**
     * 给用户送徽章，前10名注册和前100名注册
     */
    @TransactionalEventListener(classes = UserRegisterEvent.class, phase = TransactionPhase.AFTER_COMMIT)
    public void sendBadge(UserRegisterEvent event) {
        User user = event.getUser();
        userBackpackService.acquireItem(user.getId(), ItemEnum.MODIFY_NAME_CARD.getId(), IdempotentEnum.UID, user.getId().toString());
        int registeredCount = userDao.count();
        if (registeredCount < 10) {
            userBackpackService.acquireItem(user.getId(), ItemEnum.REG_TOP10_BADGE.getId(), IdempotentEnum.UID, user.getId().toString());
        } else if (registeredCount < 100) {
            userBackpackService.acquireItem(user.getId(), ItemEnum.REG_TOP100_BADGE.getId(), IdempotentEnum.UID, user.getId().toString());
        }
    }
}
