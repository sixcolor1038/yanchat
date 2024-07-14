package com.yan.yanchat.common.user.service.impl;

import com.yan.yanchat.common.infrastructure.utils.JWTUtils;
import com.yan.yanchat.common.user.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: sixcolor
 * @Date: 2024-02-15 18:16
 * @Description:
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private JWTUtils jwtUtils;

    @Override
    public boolean verify(String token) {
        return false;
    }

    @Override
    public void renewalTokenIfNecessary(String token) {

    }

    @Override
    public String login(Long uid) {
        String token = jwtUtils.createToken(uid);
        return token;
    }

    @Override
    public Long getValidUid(String token) {
        return null;
    }
}
