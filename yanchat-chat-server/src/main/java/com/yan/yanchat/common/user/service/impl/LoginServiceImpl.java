package com.yan.yanchat.common.user.service.impl;

import com.yan.yanchat.common.infrastructure.constant.RedisConstant;
import com.yan.yanchat.common.infrastructure.utils.JWTUtils;
import com.yan.yanchat.common.infrastructure.utils.RedisUtils;
import com.yan.yanchat.common.user.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @Author: sixcolor
 * @Date: 2024-02-15 18:16
 * @Description:
 */
@Service
public class LoginServiceImpl implements LoginService {

    public static final int TOKEN_EXPIRE_DAYS = 3;
    public static final int RENEW_DAY = 1;
    @Autowired
    private JWTUtils jwtUtils;

    @Override
    public boolean verify(String token) {
        return false;
    }

    @Override
    public void renewalTokenIfNecessary(String token) {
        Long uid = getValidUid(token);
        String userTokenKey = getUserTokenKey(uid);
        Long expireDay = RedisUtils.getExpire(userTokenKey, TimeUnit.DAYS);
        if (expireDay == -2) {
            return;
        }
        if (expireDay < RENEW_DAY) {
            RedisUtils.set(getUserTokenKey(uid), token, TOKEN_EXPIRE_DAYS, TimeUnit.DAYS);
        }
    }

    @Override
    public String login(Long uid) {
        String token = jwtUtils.createToken(uid);
        RedisUtils.set(getUserTokenKey(uid), token, TOKEN_EXPIRE_DAYS, TimeUnit.DAYS);
        return token;
    }

    @Override
    public Long getValidUid(String token) {
        String s = "";
        Long uid = jwtUtils.getUidOrNull(token);
        if (Objects.isNull(uid)) {
            return null;
        }
        String oldToken = RedisUtils.getStr(getUserTokenKey(uid));
        //如果旧token和新token相同，则返回uid
        return Objects.equals(oldToken, token) ? uid : null;
    }

    private String getUserTokenKey(Long uid) {
        return RedisConstant.getKey(RedisConstant.USER_TOKEN_STR, uid);
    }
}
