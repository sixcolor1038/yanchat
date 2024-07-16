package com.yan.yanchat.common.infrastructure.utils;

import com.yan.yanchat.common.infrastructure.domain.dto.RequestInfo;

/**
 * @Author: sixcolor
 * @Date: 2024-02-16 15:24
 * @Description: 请求上下文
 */
public class RequestHolder {
    private static final ThreadLocal<RequestInfo> THREAD_LOCAL = new ThreadLocal<>();

    public static void set(RequestInfo requestInfo) {
        THREAD_LOCAL.set(requestInfo);
    }

    public static RequestInfo get() {
        return THREAD_LOCAL.get();
    }

    public static void remove() {
        THREAD_LOCAL.remove();
    }
}
