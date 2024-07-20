package com.yan.yanchat.common.chat.service.impl;

import com.yan.yanchat.common.chat.dao.GroupMemberDao;
import com.yan.yanchat.common.chat.dao.RoomFriendDao;
import com.yan.yanchat.common.chat.domain.entity.GroupMember;
import com.yan.yanchat.common.chat.domain.entity.Room;
import com.yan.yanchat.common.chat.domain.entity.RoomFriend;
import com.yan.yanchat.common.chat.domain.entity.RoomGroup;
import com.yan.yanchat.common.chat.domain.vo.req.ChatMessageReq;
import com.yan.yanchat.common.chat.service.ChatService;
import com.yan.yanchat.common.chat.service.cache.RoomCache;
import com.yan.yanchat.common.chat.service.cache.RoomGroupCache;
import com.yan.yanchat.common.chat.service.strategy.msg.AbstractMsgHandler;
import com.yan.yanchat.common.chat.service.strategy.msg.MsgHandlerFactory;
import com.yan.yanchat.common.infrastructure.domain.enums.NormalOrNoEnum;
import com.yan.yanchat.common.infrastructure.event.MessageSendEvent;
import com.yan.yanchat.common.infrastructure.utils.AssertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

/**
 * @Author: sixcolor
 * @Date: 2024-03-08
 * @Description:
 */
@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    private RoomCache roomCache;
    @Autowired
    private RoomFriendDao roomFriendDao;
    @Autowired
    private RoomGroupCache roomGroupCache;
    @Autowired
    private GroupMemberDao groupMemberDao;
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public Long sendMsg(ChatMessageReq request, Long uid) {
        check(request, uid);
        AbstractMsgHandler<?> msgHandler = MsgHandlerFactory.getStrategyNoNull(request.getMsgType());
        Long msgId = msgHandler.checkAndSaveMsg(request, uid);
        //发布消息发送事件
        applicationEventPublisher.publishEvent(new MessageSendEvent(this, msgId));
        return msgId;
    }

    private void check(ChatMessageReq request, Long uid) {
        Room room = roomCache.get(request.getRoomId());
        if (room.isHotRoom()) {//全员群跳过校验
            return;
        }
        if (room.isRoomFriend()) {
            RoomFriend roomFriend = roomFriendDao.getByRoomId(request.getRoomId());
            AssertUtil.equal(NormalOrNoEnum.NORMAL.getStatus(), roomFriend.getStatus(), "您已经被对方拉黑");
            AssertUtil.isTrue(uid.equals(roomFriend.getUid1()) || uid.equals(roomFriend.getUid2()), "您已经被对方拉黑");
        }
        if (room.isRoomGroup()) {
            RoomGroup roomGroup = roomGroupCache.get(request.getRoomId());
            GroupMember member = groupMemberDao.getMember(roomGroup.getId(), uid);
            AssertUtil.isNotEmpty(member, "您已经被移除该群");
        }

    }
}
