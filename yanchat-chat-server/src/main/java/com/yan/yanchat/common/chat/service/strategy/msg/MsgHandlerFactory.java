package com.yan.yanchat.common.chat.service.strategy.msg;


import com.yan.yanchat.common.infrastructure.exception.CommonErrorEnum;
import com.yan.yanchat.common.infrastructure.utils.AssertUtil;

import java.util.HashMap;
import java.util.Map;


/**
 * @Author: sixcolor
 * @Date: 2024-03-08
 * @Description: 是否正常
 */
public class MsgHandlerFactory {
    private static final Map<Integer, AbstractMsgHandler> STRATEGY_MAP = new HashMap<>();

    public static void register(Integer code, AbstractMsgHandler strategy) {
        STRATEGY_MAP.put(code, strategy);
    }

    public static AbstractMsgHandler getStrategyNoNull(Integer code) {
        AbstractMsgHandler strategy = STRATEGY_MAP.get(code);
        AssertUtil.isNotEmpty(strategy, CommonErrorEnum.PARAM_INVALID);
        return strategy;
    }
}
