package com.yan.yanchat.common.user.service.impl;

import com.yan.yanchat.common.infrastructure.annotation.RedissonLock;
import com.yan.yanchat.common.infrastructure.domain.enums.BaseEnum;
import com.yan.yanchat.common.infrastructure.service.LockService;
import com.yan.yanchat.common.user.dao.UserBackpackDao;
import com.yan.yanchat.common.user.domain.entity.UserBackpack;
import com.yan.yanchat.common.user.domain.enums.IdempotentEnum;
import com.yan.yanchat.common.user.service.UserBackpackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @Author: sixcolor
 * @Date: 2024-02-16
 * @Description: 用户背包表 服务实现类
 */
@Service
public class UserBackpackServiceImpl implements UserBackpackService {

    @Autowired
    private LockService lockService;
    @Autowired
    private UserBackpackDao userBackpackDao;
    @Autowired
    @Lazy
    private UserBackpackServiceImpl userBackpackServiceImpl;

    @Override
    public void acquireItem(Long uid, Long itemId, IdempotentEnum idempotentEnum, String businessId) {
        //获取幂等号
        String idempotent = getIdempotent(itemId, idempotentEnum, businessId);
        userBackpackServiceImpl.doAcquireItem(uid, itemId, idempotent);
    }

    @RedissonLock(key = "#idempotent", waitTime = 5000)
    public void doAcquireItem(Long uid, Long itemId, String idempotent) {
        //查询幂等号是否存在
        UserBackpack userBackpack = userBackpackDao.getByIdempotent(idempotent);
        if (Objects.nonNull(userBackpack)) {
            //如果存在直接返回
            return;
        }
        //业务检查 如徽章不能二次发放

        //发放物品
        UserBackpack insert = UserBackpack.builder()
                .uid(uid)
                .itemId(itemId)
                .status(BaseEnum.NO.getStatus())
                .idempotent(idempotent)
                .build();
        userBackpackDao.save(insert);
    }

    /**
     * 获取幂等号
     */
    private String getIdempotent(Long itemId, IdempotentEnum idempotentEnum, String businessId) {
        return String.format("%d_%d_%s", itemId, idempotentEnum.getType(), businessId);
    }
}
