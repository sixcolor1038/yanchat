package com.yan.yanchat.common.user.service.adapter;

import cn.hutool.core.bean.BeanUtil;
import com.yan.yanchat.common.user.domain.entity.User;
import com.yan.yanchat.common.user.domain.vo.resp.UserInfoResp;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;

/**
 * @Author: sixcolor
 * @Date: 2024-02-15 16:27
 * @Description: 用户适配器
 */
public class UserAdapter {
    public static User buildUser(String openId) {
        return User.builder().openId(openId).build();
    }

    public static User buildAuthorizeUser(Long uid, WxOAuth2UserInfo userInfo) {
        return User.builder()
                .id(uid)
                .name(userInfo.getNickname())
                .avatar(userInfo.getHeadImgUrl())
                .build();
    }

    public static UserInfoResp buildUserInfo(User user, Integer modifyNameCount) {
        UserInfoResp vo = new UserInfoResp();
        BeanUtil.copyProperties(user,vo);
        vo.setModifyNameChance(modifyNameCount);
        return vo;
    }
}
