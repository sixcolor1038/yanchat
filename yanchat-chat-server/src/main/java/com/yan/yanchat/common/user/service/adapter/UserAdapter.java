package com.yan.yanchat.common.user.service.adapter;

import com.yan.yanchat.common.user.domain.entity.User;

/**
 * @Author: sixcolor
 * @Date: 2024-07-14 16:27
 * @Description:
 */
public class UserAdapter {
    public static User buildUser(String openId) {
        return User.builder().openId(openId).build();
    }
}
