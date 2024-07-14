package com.yan.yanchat.common.websocket.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.yan.yanchat.common.websocket.domain.dto.WSChannelExtraDTO;
import com.yan.yanchat.common.websocket.domain.enums.WSRespTypeEnum;
import com.yan.yanchat.common.websocket.domain.vo.resp.WSBaseResp;
import com.yan.yanchat.common.websocket.domain.vo.resp.ws.WSLoginUrl;
import com.yan.yanchat.common.websocket.service.WebSocketService;
import com.yan.yanchat.common.websocket.service.adapter.WebSocketAdapter;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.SneakyThrows;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: sixcolor
 * @Date: 2024-02-15 07:34
 * @Description: 管理websocket逻辑
 */
@Service
public class WebSocketServiceImpl implements WebSocketService {

    @Autowired
    private WxMpService wxMpService;
    /**
     * 管理所有用户的连接(包括登录态/游客)
     */
    private static final ConcurrentHashMap<Channel, WSChannelExtraDTO> ONLINE_WS_MAP = new ConcurrentHashMap<>();

    public static final Duration DURATION = Duration.ofHours(1);
    public static final int MAXIMUM_SIZE = 10000;
    /**
     * 临时保存code和channel的映射关系
     */
    private static final Cache<Integer, Channel> WAIT_LOGIN_MAP = Caffeine.newBuilder()
            .maximumSize(MAXIMUM_SIZE)
            .expireAfterWrite(DURATION)
            .build();

    /**
     * 建立websocket连接
     */
    @Override
    public void connect(Channel channel) {
        ONLINE_WS_MAP.put(channel, new WSChannelExtraDTO());
    }

    @SneakyThrows
    @Override
    public void handleLoginReq(Channel channel) {
        //生成随机码
        Integer code = generateLoginCode(channel);
        //从微信申请带参二维码
        WxMpQrCodeTicket wxMpQrCodeTicket = wxMpService.getQrcodeService().qrCodeCreateTmpTicket(code, (int) DURATION.getSeconds());
        //将二维码推送给前端

        sendMsg(channel, WebSocketAdapter.buildResp(wxMpQrCodeTicket));
    }

    private void sendMsg(Channel channel, WSBaseResp<?> resp) {
        channel.writeAndFlush(new TextWebSocketFrame(JSONUtil.toJsonStr(resp)));
    }

    //生成随机登录码
    private Integer generateLoginCode(Channel channel) {
        Integer code;
        do {
            code = RandomUtil.randomInt(Integer.MAX_VALUE);
        } while (Objects.isNull(WAIT_LOGIN_MAP.asMap().putIfAbsent(code, channel)));
        return code;
    }
}
