package com.yan.yanchat.common.infrastructure.event.listener;

import com.yan.yanchat.common.infrastructure.event.UserBlackEvent;
import com.yan.yanchat.common.user.dao.UserDao;
import com.yan.yanchat.common.user.domain.entity.User;
import com.yan.yanchat.common.user.domain.enums.IdempotentEnum;
import com.yan.yanchat.common.user.domain.enums.ItemEnum;
import com.yan.yanchat.common.user.service.UserBackpackService;
import com.yan.yanchat.common.websocket.service.WebSocketService;
import com.yan.yanchat.common.websocket.service.adapter.WebSocketAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * @Author: sixcolor
 * @Date: 2024-02-18 10:59
 * @Description: 用户拉黑监听事件
 */
@Component
public class UserBlackListener {

    @Autowired
    private UserBackpackService userBackpackService;
    @Autowired
    private UserDao userDao;
    @Autowired
    private WebSocketService webSocketService;

    /**
     * 给用户推送拉黑信息
     */
    @Async
    @TransactionalEventListener(classes = UserBlackEvent.class, phase = TransactionPhase.AFTER_COMMIT)
    public void sendMsg(UserBlackEvent event) {
        User user = event.getUser();
        webSocketService.sendMsgToAll(WebSocketAdapter.buildBlack(user));
        userBackpackService.acquireItem(user.getId(), ItemEnum.MODIFY_NAME_CARD.getId(), IdempotentEnum.UID, user.getId().toString());
    }

    @Async
    @TransactionalEventListener(classes = UserBlackEvent.class, phase = TransactionPhase.AFTER_COMMIT)
    public void changeUserStatus(UserBlackEvent event) {
        userDao.invalidUid(event.getUser().getId());
    }


}
