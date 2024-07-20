package com.yan.yanchat.common.user.service;

import com.yan.yanchat.common.user.domain.enums.RoleEnum;

/**
 * @Author: sixcolor
 * @Date: 2024-02-18
 * @Description: 角色表 服务类
 */
public interface RoleService {

    /**
     * 是否拥有某个权限(临时写法)
     *
     * @param uid      用户id
     * @param roleEnum 权限
     * @return true false
     */
    boolean hasPower(Long uid, RoleEnum roleEnum);

}
