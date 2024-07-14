package com.yan.yanchat.common.websocket.service.adapter;

import com.yan.yanchat.common.user.domain.entity.User;
import com.yan.yanchat.common.websocket.domain.enums.WSRespTypeEnum;
import com.yan.yanchat.common.websocket.domain.vo.resp.WSBaseResp;
import com.yan.yanchat.common.websocket.domain.vo.resp.ws.WSLoginSuccess;
import com.yan.yanchat.common.websocket.domain.vo.resp.ws.WSLoginUrl;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;

/**
 * @Author: sixcolor
 * @Date: 2024-02-14 07:40
 * @Description: websocket适配器
 */
public class WebSocketAdapter {


    /**
     * 将微信公众号中的二维码中的URL信息封装成一个特定格式的响应对象
     */
    public static WSBaseResp<?> buildResp(WxMpQrCodeTicket wxMpQrCodeTicket) {
        WSBaseResp<WSLoginUrl> resp = new WSBaseResp<>();
        resp.setType(WSRespTypeEnum.LOGIN_URL.getType());
        resp.setData(new WSLoginUrl(wxMpQrCodeTicket.getUrl()));
        return resp;
    }

    public static WSBaseResp<?> buildResp(User user, String token) {
        WSBaseResp<WSLoginSuccess> resp = new WSBaseResp<>();
        resp.setType(WSRespTypeEnum.LOGIN_SUCCESS.getType());
        WSLoginSuccess build = WSLoginSuccess.builder()
                .avatar(user.getAvatar())
                .name(user.getName())
                .token(token)
                .uid(user.getId())
                .build();
        resp.setData(build);
        return resp;
    }

    public static WSBaseResp<?> buildWaitAuthorizeResp() {
        WSBaseResp<WSLoginUrl> resp = new WSBaseResp<>();
        resp.setType(WSRespTypeEnum.LOGIN_SCAN_SUCCESS.getType());
        return resp;
    }
}
