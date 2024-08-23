package com.yan.yanchat.common.user.service.cache;

import cn.hutool.core.collection.CollUtil;
import com.yan.yanchat.common.infrastructure.constant.RedisKey;
import com.yan.yanchat.common.infrastructure.utils.RedisUtils;
import com.yan.yanchat.common.user.dao.BlackDao;
import com.yan.yanchat.common.user.dao.UserDao;
import com.yan.yanchat.common.user.dao.UserRoleDao;
import com.yan.yanchat.common.user.domain.entity.Black;
import com.yan.yanchat.common.user.domain.entity.User;
import com.yan.yanchat.common.user.domain.entity.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Author: sixcolor
 * @Date: 2024-02-16
 * @Description: 用户相关缓存
 */
@Component
public class UserCache {

    @Autowired
    private UserRoleDao userRoleDao;
    @Autowired
    private BlackDao blackDao;
    @Autowired
    private UserDao userDao;

    @Cacheable(cacheNames = "user", key = "'roles:'+#uid")
    public Set<Long> getRoleSet(Long uid) {
        List<UserRole> userRoles = userRoleDao.listByUid(uid);
        return userRoles.stream()
                .map(UserRole::getRoleId)
                .collect(Collectors.toSet());
    }

    @Cacheable(cacheNames = "user", key = "'blackList'")
    public Map<Integer, Set<String>> getBlackMap() {
        //根据拉黑类型分组
        Map<Integer, List<Black>> collect = blackDao.list().stream().collect(Collectors.groupingBy(Black::getType));
        //初始化一个新map，并进行转换
        Map<Integer, Set<String>> result = new HashMap<>();
        collect.forEach((type, list) -> {
            result.put(type, list.stream().map(Black::getTarget).collect(Collectors.toSet()));
        });
        return result;
    }

    @CacheEvict(cacheNames = "user", key = "'blackList'")
    public Map<Integer, Set<String>>  evictBlackMap() {
        return null;
    }

    /**
     * 获取用户信息，盘路缓存模式
     */
    public User getUserInfo(Long uid) {//todo 后期做二级缓存
        return getUserInfoBatch(Collections.singleton(uid)).get(uid);
    }

    /**
     * 获取用户信息，盘路缓存模式
     */
    public Map<Long, User> getUserInfoBatch(Set<Long> uids) {
        //批量组装key
        List<String> keys = uids.stream().map(a -> RedisKey.getKey(RedisKey.USER_INFO_STRING, a)).collect(Collectors.toList());
        //批量get
        List<User> mget = RedisUtils.mget(keys, User.class);
        Map<Long, User> map = mget.stream().filter(Objects::nonNull).collect(Collectors.toMap(User::getId, Function.identity()));
        //发现差集——还需要load更新的uid
        List<Long> needLoadUidList = uids.stream().filter(a -> !map.containsKey(a)).collect(Collectors.toList());
        if (CollUtil.isNotEmpty(needLoadUidList)) {
            //批量load
            List<User> needLoadUserList = userDao.listByIds(needLoadUidList);
            Map<String, User> redisMap = needLoadUserList.stream()
                    .collect(Collectors
                            .toMap(a -> RedisKey.getKey(RedisKey.USER_INFO_STRING, a.getId()), Function.identity()));
            RedisUtils.mset(redisMap, 5 * 60);
            //加载回redis
            map.putAll(needLoadUserList.stream().collect(Collectors.toMap(User::getId, Function.identity())));
        }
        return map;
    }
}
