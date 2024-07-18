package com.yan.yanchat.common.infrastructure.event.listener;

import com.yan.yanchat.common.infrastructure.event.UserOnlineEvent;
import com.yan.yanchat.common.infrastructure.event.UserRegisterEvent;
import com.yan.yanchat.common.user.dao.UserDao;
import com.yan.yanchat.common.user.domain.entity.User;
import com.yan.yanchat.common.user.domain.enums.IdempotentEnum;
import com.yan.yanchat.common.user.domain.enums.ItemEnum;
import com.yan.yanchat.common.user.domain.enums.UserActiveStatusEnum;
import com.yan.yanchat.common.user.service.IpService;
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
public class UserOnlineListener {

    @Autowired
    private UserBackpackService userBackpackService;
    @Autowired
    private UserDao userDao;
    @Autowired
    private IpService ipService;

    /**
     * 发放改名卡
     * 异步执行
     * 在事务提交后执行，不能影响用户注册的事务
     */
    @Async
    @TransactionalEventListener(classes = UserOnlineEvent.class, phase = TransactionPhase.AFTER_COMMIT)
    public void saveDB(UserRegisterEvent event) {
        User user = event.getUser();
        User update = new User();
        update.setId(user.getId());
        update.setLastOptTime(user.getLastOptTime());
        update.setIpInfo(user.getIpInfo());
        update.setActiveStatus(UserActiveStatusEnum.ONLINE.getStatus());
        userDao.updateById(update);
        //用户IP详情解析
        ipService.refreshIpDetailAsync(user.getId());
    }


}
