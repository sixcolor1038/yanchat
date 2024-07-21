package com.yan.yanchat.common.user.service;

import com.yan.yanchat.common.infrastructure.domain.vo.req.CursorPageBaseReq;
import com.yan.yanchat.common.infrastructure.domain.vo.req.PageBaseReq;
import com.yan.yanchat.common.infrastructure.domain.vo.resp.CursorPageBaseResp;
import com.yan.yanchat.common.infrastructure.domain.vo.resp.PageBaseResp;
import com.yan.yanchat.common.user.domain.vo.req.friend.FriendApplyReq;
import com.yan.yanchat.common.user.domain.vo.req.friend.FriendApproveReq;
import com.yan.yanchat.common.user.domain.vo.resp.FriendResp;
import com.yan.yanchat.common.user.domain.vo.resp.friend.FriendApplyResp;

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
     *
     * @param uid       用户id
     * @param targetUid 好友用户id
     */
    void deleteFriend(Long uid, Long targetUid);

    /**
     * 好友列表
     *
     * @param uid
     * @param request
     * @return
     */
    CursorPageBaseResp<FriendResp> friendList(Long uid, CursorPageBaseReq request);

    /**
     * 分页查询好友申请
     *
     * @param uid 用户id
     * @param request 请求
     * @return
     */
    PageBaseResp<FriendApplyResp> pageApplyFriend(Long uid, PageBaseReq request);
}
