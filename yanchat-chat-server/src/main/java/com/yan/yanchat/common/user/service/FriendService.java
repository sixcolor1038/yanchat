package com.yan.yanchat.common.user.service;

import com.yan.yanchat.common.user.domain.vo.req.FriendApplyReq;
import com.yan.yanchat.common.user.domain.vo.req.FriendApproveReq;

/**
 * @Author: sixcolor
 * @Date: 2024-02-18
 * @Description:
 */
public interface FriendService {
    void apply(Long uid, FriendApplyReq req);

    /**
     * 同意好友申请
     *
     * @param uid     uid
     * @param request 请求
     */
    void applyApprove(Long uid, FriendApproveReq request);

    /**
     * 删除好友
     * @param uid 用户id
     * @param targetUid 好友用户id
     */
    void deleteFriend(Long uid, Long targetUid);
}
