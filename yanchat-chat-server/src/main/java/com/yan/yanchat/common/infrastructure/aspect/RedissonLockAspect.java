package com.yan.yanchat.common.infrastructure.aspect;

import com.yan.yanchat.common.infrastructure.annotation.RedissonLock;
import com.yan.yanchat.common.infrastructure.service.LockService;
import com.yan.yanchat.common.infrastructure.utils.SpringELUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @Author: sixcolor
 * @Date: 2024-02-17
 * @Description:
 */
@Aspect
@Component
@Order(0) //确保比事务注解先执行，分布式锁在事务外
public class RedissonLockAspect {

    @Autowired
    private LockService lockService;

    @Around("@annotation(redissonLock)")
    public Object around(ProceedingJoinPoint joinPoint, RedissonLock redissonLock) {
        //获取方法
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        String prefix = StringUtils.isBlank(redissonLock.prefixKey()) ? SpringELUtils.getMethodKey(method) : redissonLock.prefixKey();
        String key = SpringELUtils.parseEl(method, joinPoint.getArgs(), redissonLock.key());

        return lockService.executeWithLock(prefix + ":" + key, redissonLock.waitTime(), redissonLock.timeunit(), joinPoint::proceed);
    }
}
