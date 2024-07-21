package com.yan.yanchat.common.user.service.adapter;

import com.yan.yanchat.common.user.domain.entity.User;
import com.yan.yanchat.common.user.domain.entity.UserApply;
import com.yan.yanchat.common.user.domain.entity.UserFriend;
import com.yan.yanchat.common.user.domain.vo.resp.friend.FriendResp;
import com.yan.yanchat.common.user.domain.vo.resp.friend.FriendApplyResp;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Author: sixcolor
 * @Date: 2024-04-28
 * @Description:
 */
public class FriendAdapter {
    public static List<FriendResp> buildFriend(List<UserFriend> list, List<User> userList) {
        Map<Long, User> userMap = userList.stream().collect(Collectors.toMap(User::getId, user -> user));
        return list.stream().map(userFriend -> {
            FriendResp resp = new FriendResp();
            resp.setUid(userFriend.getFriendUid());
            User user = userMap.get(userFriend.getFriendUid());
            if (Objects.nonNull(user)) {
                resp.setActiveStatus(user.getActiveStatus());
            }
            return resp;
        }).collect(Collectors.toList());
    }

    public static List<FriendApplyResp> buildFriendApplyList(List<UserApply> records) {
        return records.stream().map(userApply -> {
            FriendApplyResp friendApplyResp = new FriendApplyResp();
            friendApplyResp.setUid(userApply.getUid());
            friendApplyResp.setType(userApply.getType());
            friendApplyResp.setApplyId(userApply.getId());
            friendApplyResp.setMsg(userApply.getMsg());
            friendApplyResp.setStatus(userApply.getStatus());
            return friendApplyResp;
        }).collect(Collectors.toList());
    }
}
