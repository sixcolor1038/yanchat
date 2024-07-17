package com.yan.yanchat.common.user.service.impl;

import com.yan.yanchat.common.infrastructure.domain.enums.BaseEnum;
import com.yan.yanchat.common.infrastructure.utils.AssertUtil;
import com.yan.yanchat.common.user.dao.UserBackpackDao;
import com.yan.yanchat.common.user.domain.entity.UserBackpack;
import com.yan.yanchat.common.user.domain.enums.IdempotentEnum;
import com.yan.yanchat.common.user.service.UserBackpackService;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @Author: sixcolor
 * @Date: 2024-02-16 08:16
 * @Description: 用户背包表 服务实现类
 */
@Service
public class UserBackpackServiceImpl implements UserBackpackService {

    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private UserBackpackDao userBackpackDao;

    @Override
    public void acquireItem(Long uid, Long itemId, IdempotentEnum idempotentEnum, String businessId) {
        //获取幂等号
        String idempotent = getIdempotent(itemId, idempotentEnum, businessId);
        //获取锁
        RLock lock = redissonClient.getLock("acquireItem" + idempotent);
        boolean tried = lock.tryLock();
        AssertUtil.isTrue(tried, "请求太频繁了");
        try {
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

        } finally {
            //释放锁
            lock.unlock();
        }
    }

    /**
     * 获取幂等号
     */
    private String getIdempotent(Long itemId, IdempotentEnum idempotentEnum, String businessId) {
        return String.format("%d_%d_%s", itemId, idempotentEnum.getType(), businessId);
    }
}
