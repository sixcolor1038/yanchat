package com.yan.yanchat.common.infrastructure.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * @Author: sixcolor
 * @Date: 2024-02-17 9:35
 * @Description: 分布式锁-注解式
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RedissonLock {

    /**
     * key的前缀，默认取方法全限定名，可以自己指定
     */
    String prefixKey() default "";

    /**
     * 支持springEL表达式的key
     */
    String key();

    /**
     * 等待锁的排队时间，默认不等待，快速失败
     */
    int waitTime() default -1;

    /**
     * 时间单位，默认毫秒
     */
    TimeUnit timeunit() default TimeUnit.MILLISECONDS;
}
