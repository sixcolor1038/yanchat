package com.yan.yanchat.common.chat.service.strategy.msg;

import com.yan.yanchat.common.chat.dao.MessageDao;
import com.yan.yanchat.common.chat.domain.entity.Message;
import com.yan.yanchat.common.chat.domain.entity.msg.MessageExtra;
import com.yan.yanchat.common.chat.domain.entity.msg.SoundMsgDTO;
import com.yan.yanchat.common.chat.domain.enums.MessageTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @Author: sixcolor
 * @Date: 2024-03-29
 * @Description: 图片消息
 */
@Component
public class SoundMsgHandler extends AbstractMsgHandler<SoundMsgDTO> {
    @Autowired
    private MessageDao messageDao;

    @Override
    MessageTypeEnum getMsgTypeEnum() {
        return MessageTypeEnum.SOUND;
    }

    @Override
    public void saveMsg(Message msg, SoundMsgDTO body) {
        MessageExtra extra = Optional.ofNullable(msg.getExtra()).orElse(new MessageExtra());
        Message update = new Message();
        update.setId(msg.getId());
        update.setExtra(extra);
        extra.setSoundMsgDTO(body);
        messageDao.updateById(update);
    }

    @Override
    public Object showMsg(Message msg) {
        return msg.getExtra().getSoundMsgDTO();
    }

    @Override
    public Object showReplyMsg(Message msg) {
        return "语音";
    }

    @Override
    public String showContactMsg(Message msg) {
        return "[语音]";
    }
}
