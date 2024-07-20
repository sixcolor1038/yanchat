package com.yan.yanchat.common.chat.service;

import com.yan.yanchat.common.chat.domain.entity.RoomFriend;

import java.util.List;

/**
 * @Author: sixcolor
 * @Date: 2024-02-27
 * @Description: 房间底层管理
 */
public interface RoomService {
    RoomFriend createFriendRoom(List<Long> list);
}
