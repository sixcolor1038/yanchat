package com.yan.yanchat.common.chat.service.cache;

import com.yan.yanchat.common.chat.dao.RoomDao;
import com.yan.yanchat.common.chat.dao.RoomFriendDao;
import com.yan.yanchat.common.chat.domain.entity.Room;
import com.yan.yanchat.common.infrastructure.constant.RedisConstant;
import com.yan.yanchat.common.infrastructure.service.cache.AbstractRedisStringCache;
import com.yan.yanchat.common.user.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Author: sixcolor
 * @Date: 2024-03-08
 * @Description:
 */
@Component
public class RoomCache extends AbstractRedisStringCache<Long, Room> {
    @Autowired
    private UserDao userDao;
    @Autowired
    private RoomDao roomDao;
    @Autowired
    private RoomFriendDao roomFriendDao;

    @Override
    protected String getKey(Long roomId) {
        return RedisConstant.getKey(RedisConstant.ROOM_INFO_STRING, roomId);
    }

    @Override
    protected Long getExpireSeconds() {
        return 5 * 60L;
    }

    @Override
    protected Map<Long, Room> load(List<Long> roomIds) {
        List<Room> rooms = roomDao.listByIds(roomIds);
        return rooms.stream().collect(Collectors.toMap(Room::getId, Function.identity()));
    }
}
