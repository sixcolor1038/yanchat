package com.yan.yanchat.common.infrastructure.service;

import com.yan.yanchat.common.infrastructure.exception.BusinessException;
import com.yan.yanchat.common.infrastructure.exception.CommonErrorEnum;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * @Author: sixcolor
 * @Date: 2024-02-16 20:53
 * @Description:
 */
@Service
public class LockService {
    @Autowired
    private RedissonClient redissonClient;

    public <T> T executeWithLock(String key, int waitTime, TimeUnit timeUnit, Supplier<T> supplier) {
        RLock lock = redissonClient.getLock(key);
        boolean locked = false;
        try {
            locked = lock.tryLock(waitTime, timeUnit);
            if (!locked) {
                throw new BusinessException(CommonErrorEnum.LOCK_LIMIT);
            }
            return supplier.get();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // 恢复中断状态
            throw new RuntimeException(e);
        } finally {
            if (locked) {
                try {
                    lock.unlock();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public <T> T executeWithLock(String key, Supplier<T> supplier) {
        return executeWithLock(key, -1, TimeUnit.MILLISECONDS, supplier);
    }

    public <T> T executeWithLock(String key, Runnable runnable) {
        return executeWithLock(key, -1, TimeUnit.MILLISECONDS, () -> {
            runnable.run();
            return null;
        });
    }
}
