package com.yan.yanchat.common.user.service.impl;

import com.yan.yanchat.common.user.dao.UserDao;
import com.yan.yanchat.common.user.domain.entity.User;
import com.yan.yanchat.common.user.service.UserService;
import com.yan.yanchat.common.user.service.WxMsgService;
import com.yan.yanchat.common.user.service.adapter.TextBuilder;
import com.yan.yanchat.common.user.service.adapter.UserAdapter;
import com.yan.yanchat.common.websocket.service.WebSocketService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: sixcolor
 * @Date: 2024-02-14
 * @Description:
 */
@Service
@Slf4j
public class WxMsgServiceImpl implements WxMsgService {

    /**
     * openid和登录code关系map
     */
    private static final ConcurrentHashMap<String, Integer> WAIT_AUTHORIZE_MAP = new ConcurrentHashMap<>();
    private static final String URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";

    @Value("${wx.mp.callback}")
    private String callback;

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserService userService;

    @Autowired
    @Lazy
    private WxMpService wxMpService;

    @Autowired
    private WebSocketService webSocketService;

    @Override
    public WxMpXmlOutMessage scan(WxMpService wxMpService, WxMpXmlMessage wxMessage) {
        String openId = wxMessage.getFromUser();
        Integer code = getEventKey(wxMessage);
        if (Objects.isNull(code)) {
            return null;
        }
        User user = userDao.getByOpenId(openId);
        //已注册
        boolean registered = Objects.nonNull(user);
        //已授权
        boolean authorized = registered && StringUtils.isNotBlank(user.getAvatar());
        //用户已注册并授权
        if (registered && authorized) {
            //走登录成功逻辑，通过code找到给channel推送消息
            webSocketService.scanLoginSuccess(code, user.getId());
            return null;
        }
        //如果用户未注册，就先注册
        if (!registered) {
            User insert = UserAdapter.buildUser(openId);
            userService.register(insert);
        }

        //推送链接让用户授权
        WAIT_AUTHORIZE_MAP.put(openId, code);
        webSocketService.waitAuthorize(code);
        String authorizeUrl = String.format(URL, wxMpService.getWxMpConfigStorage().getAppId(), URLEncoder.encode(callback + "/wx/portal/public/callBack"));
        // 扫码事件处理
        return TextBuilder.build("请点击链接授权：<a href=\"" + authorizeUrl + "\">登录</a>", wxMessage);

    }

    @Override
    public void authorize(WxOAuth2UserInfo userInfo) {
        String openid = userInfo.getOpenid();
        User user = userDao.getByOpenId(openid);
        //更新用户信息
        if (StringUtils.isBlank(user.getName())) {
            fillUserinfo(user.getId(), userInfo);
        }
        //通过code找到用户channel进行登录
        Integer code = WAIT_AUTHORIZE_MAP.remove(openid);
        webSocketService.scanLoginSuccess(code, user.getId());
    }

    //更新用户信息
    private void fillUserinfo(Long uid, WxOAuth2UserInfo userInfo) {
        User user = UserAdapter.buildAuthorizeUser(uid, userInfo);
        userDao.updateById(user);
    }

    private Integer getEventKey(WxMpXmlMessage wxMessage) {
        try {
            String eventKey = wxMessage.getEventKey();
            String code = eventKey.replace("qrscene_", "");
            return Integer.parseInt(code);
        } catch (Exception e) {
            log.error("getEventKey error eventKey:{}", wxMessage.getEventKey(), e);
            return null;
        }
    }
}
