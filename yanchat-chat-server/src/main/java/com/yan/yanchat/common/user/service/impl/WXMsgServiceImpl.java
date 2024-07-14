package com.yan.yanchat.common.user.service.impl;

import com.yan.yanchat.common.user.dao.UserDao;
import com.yan.yanchat.common.user.domain.entity.User;
import com.yan.yanchat.common.user.service.UserService;
import com.yan.yanchat.common.user.service.WXMsgService;
import com.yan.yanchat.common.user.service.adapter.TextBuilder;
import com.yan.yanchat.common.user.service.adapter.UserAdapter;
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

/**
 * @Author: sixcolor
 * @Date: 2024-02-14 07:45
 * @Description:
 */
@Service
@Slf4j
public class WXMsgServiceImpl implements WXMsgService {

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

    @Override
    public WxMpXmlOutMessage scan(WxMpXmlMessage wxMessage) {
        String openId = wxMessage.getFromUser();
        Integer code = getEventKey(wxMessage);
        if (Objects.isNull(code)) {
            return null;
        }
        User user = userDao.getByOpenId(openId);
        //已注册
        boolean registered = Objects.nonNull(user);
        //已授权
        boolean authorized = registered && StringUtils.isNotBlank(user.getName());
        //用户已注册并授权
        if (registered && authorized) {
            //走登录成功逻辑，通过code找到给channel推送消息
            return null;
        }
        //如果用户未注册，就先注册
        if (!registered) {
            User insert = UserAdapter.buildUser(openId);
            Long register = userService.register(insert);
        }

        //推送链接让用户授权
        String authorizeUrl = String.format(URL, wxMpService.getWxMpConfigStorage().getAppId(), URLEncoder.encode(callback + "/wx/portal/public/callBack"));
        // 扫码事件处理
        return TextBuilder.build("请点击链接授权：<a href=\"" + authorizeUrl + "\">登录</a>", wxMessage);

    }

    @Override
    public void authorize(WxOAuth2UserInfo userInfo) {

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
