package com.yan.yanchat.common.user.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yan.yanchat.common.infrastructure.domain.vo.req.CursorPageBaseReq;
import com.yan.yanchat.common.infrastructure.domain.vo.resp.CursorPageBaseResp;
import com.yan.yanchat.common.infrastructure.utils.CursorUtils;
import com.yan.yanchat.common.user.domain.entity.UserFriend;
import com.yan.yanchat.common.user.mapper.UserFriendMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: sixcolor
 * @Date: 2024-02-18
 * @Description:
 */
@Service
public class UserFriendDao extends ServiceImpl<UserFriendMapper, UserFriend> {
    public UserFriend getByFriend(Long uid, Long targetUid) {
        return lambdaQuery()
                .eq(UserFriend::getUid, uid)
                .eq(UserFriend::getFriendUid, targetUid)
                .one();
    }

    public List<UserFriend> getUserFriend(Long uid, Long friendUid) {
        return lambdaQuery()
                .eq(UserFriend::getUid, uid)
                .eq(UserFriend::getFriendUid, friendUid)
                .or()
                .eq(UserFriend::getFriendUid, uid)
                .eq(UserFriend::getUid, friendUid)
                .select(UserFriend::getId)
                .list();
    }

    public CursorPageBaseResp<UserFriend> getFriendPage(Long uid, CursorPageBaseReq request) {
        return CursorUtils
                .getCursorPageByMysql(this, request,
                wrapper -> wrapper.eq(UserFriend::getUid, uid), UserFriend::getId);
    }

    public List<UserFriend> getByFriends(Long uid, List<Long> uidList) {
        return lambdaQuery()
                .eq(UserFriend::getUid, uid)
                .in(UserFriend::getFriendUid, uidList)
                .list();
    }
}
