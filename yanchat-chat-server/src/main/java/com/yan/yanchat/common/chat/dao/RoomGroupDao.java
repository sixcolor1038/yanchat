package com.yan.yanchat.common.chat.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yan.yanchat.common.chat.domain.entity.RoomGroup;
import com.yan.yanchat.common.chat.mapper.RoomGroupMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: sixcolor
 * @Date: 2024-03-08
 * @Description:
 */
@Service
public class RoomGroupDao extends ServiceImpl<RoomGroupMapper, RoomGroup> {
    public List<RoomGroup> listByRoomIds(List<Long> roomIds) {
        return lambdaQuery()
                .in(RoomGroup::getRoomId, roomIds)
                .list();
    }

    public RoomGroup getByRoomId(Long roomId) {
        return lambdaQuery()
                .eq(RoomGroup::getRoomId, roomId)
                .one();
    }
}
