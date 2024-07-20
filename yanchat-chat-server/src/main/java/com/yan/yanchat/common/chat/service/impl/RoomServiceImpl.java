package com.yan.yanchat.common.chat.service.impl;

import com.yan.yanchat.common.chat.dao.RoomDao;
import com.yan.yanchat.common.chat.dao.RoomFriendDao;
import com.yan.yanchat.common.chat.domain.entity.Room;
import com.yan.yanchat.common.chat.domain.entity.RoomFriend;
import com.yan.yanchat.common.chat.domain.enums.RoomTypeEnum;
import com.yan.yanchat.common.chat.service.RoomService;
import com.yan.yanchat.common.chat.service.adapter.ChatAdapter;
import com.yan.yanchat.common.infrastructure.domain.enums.NormalOrNoEnum;
import com.yan.yanchat.common.infrastructure.utils.AssertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @Author: sixcolor
 * @Date: 2024-02-27
 * @Description: 房间 服务实现类
 */
@Service
public class RoomServiceImpl implements RoomService {
    @Autowired
    private RoomFriendDao roomFriendDao;
    @Autowired
    private RoomDao roomDao;

    @Override
    public RoomFriend createFriendRoom(List<Long> uidList) {
        AssertUtil.isNotEmpty(uidList, "房间创建失败，好友数量不对");
        AssertUtil.equal(uidList.size(), 2, "房间创建失败，好友数量不对");
        String key = ChatAdapter.generateRoomKey(uidList);

        RoomFriend roomFriend = roomFriendDao.getByKey(key);
        if (Objects.nonNull(roomFriend)) {
            //如果存在房间就恢复，适用于恢复好友场景
            restoreRoomIfNeed(roomFriend);
        } else {
            //新建房间
            Room room = createRoom(RoomTypeEnum.FRIEND);
            roomFriend = createFriendRoom(room.getId(), uidList);
        }
        return roomFriend;
    }

    @Override
    public void disableFriendRoom(List<Long> uidList) {
        AssertUtil.isNotEmpty(uidList, "房间创建失败，好友数量不对");
        AssertUtil.equal(uidList.size(), 2, "房间创建失败，好友数量不对");
        String key = ChatAdapter.generateRoomKey(uidList);
        roomFriendDao.disableRoom(key);
    }

    private RoomFriend createFriendRoom(Long roomId, List<Long> uidList) {
        RoomFriend insert = ChatAdapter.buildFriendRoom(roomId, uidList);
        roomFriendDao.save(insert);
        return insert;
    }

    private Room createRoom(RoomTypeEnum roomTypeEnum) {
        Room insert = ChatAdapter.buildRoom(roomTypeEnum);
        roomDao.save(insert);
        return insert;
    }

    private void restoreRoomIfNeed(RoomFriend roomFriend) {
        if (Objects.equals(roomFriend.getStatus(), NormalOrNoEnum.NOT_NORMAL.getStatus())) {
            roomFriendDao.restoreRoom(roomFriend.getId());
        }
    }
}
