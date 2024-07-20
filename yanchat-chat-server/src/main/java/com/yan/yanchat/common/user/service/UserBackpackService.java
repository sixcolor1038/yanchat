package com.yan.yanchat.common.user.service;

import com.yan.yanchat.common.user.domain.enums.IdempotentEnum;

/**
 * @Author: sixcolor
 * @Date: 2024-02-16
 * @Description: 用户背包表 服务类
 */
public interface UserBackpackService {

    /**
     * 给用户发放一个物品
     *
     * @param uid            用户id
     * @param itemId         物品id
     * @param idempotentEnum 幂等类型
     * @param businessId     幂等唯一标识
     */
    void acquireItem(Long uid, Long itemId, IdempotentEnum idempotentEnum, String businessId);

}
