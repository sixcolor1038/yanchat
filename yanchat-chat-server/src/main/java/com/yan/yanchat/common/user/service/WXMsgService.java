package com.yan.yanchat.common.user.service;

import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;

/**
 * @Author: sixcolor
 * @Date: 2024-02-14 07:45
 * @Description:
 */
public interface WXMsgService {
    /**
     * 用户扫码成功
     * @param wxMessage
     * @return
     */
    WxMpXmlOutMessage scan(WxMpXmlMessage wxMessage);

    /**
     * 用户授权
     * @param userInfo
     */
    void authorize(WxOAuth2UserInfo userInfo);
}
