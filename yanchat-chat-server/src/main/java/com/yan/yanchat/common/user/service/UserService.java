package com.yan.yanchat.common.user.service;

import com.yan.yanchat.common.user.domain.entity.User;
import com.yan.yanchat.common.user.domain.vo.resp.UserInfoResp;
import org.springframework.stereotype.Service;


/**
 * @Author: sixcolor
 * @Date: 2024-02-14 18:22
 * @Description: 用户表 服务类
 */
@Service
public interface UserService {

    Long register(User insert);

    UserInfoResp getUserInfo(Long uid);
}
