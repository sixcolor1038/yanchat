package com.yan.yanchat.common.chat.service.adapter;

import com.yan.yanchat.common.chat.domain.entity.Message;
import com.yan.yanchat.common.chat.domain.enums.MessageStatusEnum;
import com.yan.yanchat.common.chat.domain.enums.MessageTypeEnum;
import com.yan.yanchat.common.chat.domain.vo.req.ChatMessageReq;
import com.yan.yanchat.common.chat.domain.vo.req.TextMsgReq;

/**
 * @Author: sixcolor
 * @Date: 2024-02-20
 * @Description:
 */
public class MessageAdapter {
    public static Message buildMsgSave(ChatMessageReq request, Long uid) {
        return Message.builder()
                .fromUid(uid)
                .roomId(request.getRoomId())
                .type(request.getMsgType())
                .status(MessageStatusEnum.NORMAL.getStatus())
                .build();
    }

    public static ChatMessageReq buildAgreeMsg(Long roomId) {
        ChatMessageReq chatMessageReq = new ChatMessageReq();
        chatMessageReq.setRoomId(roomId);
        chatMessageReq.setMsgType(MessageTypeEnum.TEXT.getType());
        TextMsgReq textMsgReq = new TextMsgReq();
        textMsgReq.setContent("我们已经成为好友了，开始聊天吧");
        chatMessageReq.setBody(textMsgReq);
        return chatMessageReq;
    }
}
