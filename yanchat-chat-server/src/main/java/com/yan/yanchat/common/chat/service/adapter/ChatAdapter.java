package com.yan.yanchat.common.chat.service.adapter;

import com.yan.yanchat.common.chat.domain.entity.Room;
import com.yan.yanchat.common.chat.domain.entity.RoomFriend;
import com.yan.yanchat.common.chat.domain.enums.HotFlagEnum;
import com.yan.yanchat.common.chat.domain.enums.RoomTypeEnum;
import com.yan.yanchat.common.infrastructure.domain.enums.NormalOrNoEnum;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: sixcolor
 * @Date: 2024-02-27
 * @Description:
 */
public class ChatAdapter {
    public static final String SEPARATOR = ",";
    public static String generateRoomKey(List<Long> uidList) {
        return uidList.stream()
                .sorted()
                .map(String::valueOf)
                .collect(Collectors.joining(SEPARATOR));
    }

    public static Room buildRoom(RoomTypeEnum typeEnum) {
        Room room = new Room();
        room.setType(typeEnum.getType());
        room.setHotFlag(HotFlagEnum.NOT.getType());
        return room;
    }

    public static RoomFriend buildFriendRoom(Long roomId, List<Long> uidList) {
        List<Long> collect = uidList.stream().sorted().collect(Collectors.toList());
        RoomFriend roomFriend = new RoomFriend();
        roomFriend.setRoomId(roomId);
        roomFriend.setUid1(collect.get(0));
        roomFriend.setUid2(collect.get(1));
        roomFriend.setRoomKey(generateRoomKey(uidList));
        roomFriend.setStatus(NormalOrNoEnum.NORMAL.getStatus());
        return roomFriend;
    }
}
