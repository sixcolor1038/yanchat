package com.yan.yanchat.common.user.service;

import com.yan.yanchat.common.user.domain.entity.User;
import com.yan.yanchat.common.user.domain.vo.req.user.BlackReq;
import com.yan.yanchat.common.user.domain.vo.resp.BadgeResp;
import com.yan.yanchat.common.user.domain.vo.resp.UserInfoResp;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: sixcolor
 * @Date: 2024-02-14
 * @Description: 用户表 服务类
 */
@Service
public interface UserService {

    Long register(User insert);

    UserInfoResp getUserInfo(Long uid);

    void modifyName(Long uid, String name);

    List<BadgeResp> badges(Long uid);

    void wearingBadge(Long uid, Long itemId);

    void black(BlackReq req);
}
