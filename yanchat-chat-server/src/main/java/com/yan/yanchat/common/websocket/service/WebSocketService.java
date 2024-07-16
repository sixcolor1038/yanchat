package com.yan.yanchat.common.websocket.service;

import io.netty.channel.Channel;

/**
 * @Author: sixcolor
 * @Date: 2024-02-15 07:29
 * @Description:
 */
public interface WebSocketService {
    void connect(Channel channel);

    void handleLoginReq(Channel channel);

    void remove(Channel channel);

    void scanLoginSuccess(Integer code, Long uid);

    void waitAuthorize(Integer code);

    void authorize(Channel channel, String token);
}
