package com.yan.yanchat.common.websocket;

import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONUtil;
import com.yan.yanchat.common.websocket.domain.enums.WSReqTypeEnum;
import com.yan.yanchat.common.websocket.domain.vo.req.WSBaseReq;
import com.yan.yanchat.common.websocket.service.WebSocketService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: sixcolor
 * @Date: 2024-02-13 18:22
 * @Description:
 */
@Slf4j
@Sharable
public class NettyWebSocketServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {


    private WebSocketService webSocketService;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        webSocketService = SpringUtil.getBean(WebSocketService.class);
        webSocketService.connect(ctx.channel());
    }

    /**
     * 用户主动下线
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        userOffLine(ctx.channel());
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof WebSocketServerProtocolHandler.HandshakeComplete) {
            log.info("握手完成");
        } else if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE) {
                log.info("读空闲");
                userOffLine(ctx.channel());
            }
        }
    }

    /**
     * 用户下线统一处理
     *
     * @param channel
     */
    private void userOffLine(Channel channel) {
        webSocketService.remove(channel);
        //用户下线
        channel.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        String text = msg.text();
        WSBaseReq wsBaseReq = JSONUtil.toBean(text, WSBaseReq.class);
        switch (WSReqTypeEnum.of(wsBaseReq.getType())) {
            case LOGIN:
                //登录逻辑
                webSocketService.handleLoginReq(ctx.channel());
                log.info("请求二维码");
                ctx.channel().writeAndFlush(new TextWebSocketFrame("success"));
                break;
            case AUTHORIZE:
                break;
            case HEARTBEAT:
                break;
        }
    }
}
