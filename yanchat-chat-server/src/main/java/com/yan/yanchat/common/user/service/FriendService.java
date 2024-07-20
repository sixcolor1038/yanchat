package com.yan.yanchat.common.user.service;

import com.yan.yanchat.common.user.domain.vo.req.FriendApplyReq;

/**
 * @Author: sixcolor
 * @Date: 2024-02-18 15:18
 * @Description:
 */
public interface FriendService {
    void apply(Long uid, FriendApplyReq req);
}
