package com.yan.yanchat.common.chat.controller;

import com.yan.yanchat.common.chat.domain.vo.req.ChatMessageReq;
import com.yan.yanchat.common.chat.domain.vo.resp.ChatMessageResp;
import com.yan.yanchat.common.chat.service.ChatService;
import com.yan.yanchat.common.infrastructure.domain.vo.resp.ApiResult;
import com.yan.yanchat.common.infrastructure.utils.RequestHolder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @Author: sixcolor
 * @Date: 2024-07-28
 * @Description: 聊天模块
 */
@Slf4j
@RestController
@RequestMapping("/capi/chat")
@Api(tags = "聊天相关接口")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @ApiOperation("发送消息")
    @PostMapping("/msg")
    public ApiResult<ChatMessageResp> sendMsg(@Valid @RequestBody ChatMessageReq req) {
        Long msgID = chatService.sendMsg(req, RequestHolder.get().getUid());
        return ApiResult.success();
    }

    @GetMapping("/hello")
    public String hello(String name){
        return "Hello "+name;
    }

}
