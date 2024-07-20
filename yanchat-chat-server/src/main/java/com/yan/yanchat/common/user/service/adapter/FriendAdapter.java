package com.yan.yanchat.common.user.service.adapter;

import com.yan.yanchat.common.user.domain.entity.User;
import com.yan.yanchat.common.user.domain.entity.UserFriend;
import com.yan.yanchat.common.user.domain.vo.resp.FriendResp;

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
}
