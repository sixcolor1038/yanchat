package com.yan.yanchat.common.user.service.cache;


import com.yan.yanchat.common.infrastructure.constant.RedisConstant;
import com.yan.yanchat.common.infrastructure.service.cache.AbstractRedisStringCache;
import com.yan.yanchat.common.user.dao.UserDao;
import com.yan.yanchat.common.user.domain.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Author: sixcolor
 * @Date: 2024-03-29
 * @Description: 用户基本信息的缓存
 */
@Component
public class UserInfoCache extends AbstractRedisStringCache<Long, User> {
    @Autowired
    private UserDao userDao;

    @Override
    protected String getKey(Long uid) {
        return RedisConstant.getKey(RedisConstant.USER_INFO_STRING, uid);
    }

    @Override
    protected Long getExpireSeconds() {
        return 5 * 60L;
    }

    @Override
    protected Map<Long, User> load(List<Long> uidList) {
        List<User> needLoadUserList = userDao.listByIds(uidList);
        return needLoadUserList.stream().collect(Collectors.toMap(User::getId, Function.identity()));
    }
}
