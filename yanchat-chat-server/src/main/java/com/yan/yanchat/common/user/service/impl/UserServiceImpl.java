package com.yan.yanchat.common.user.service.impl;

import com.yan.yanchat.common.user.dao.UserBackpackDao;
import com.yan.yanchat.common.user.dao.UserDao;
import com.yan.yanchat.common.user.domain.entity.User;
import com.yan.yanchat.common.user.domain.enums.ItemEnum;
import com.yan.yanchat.common.user.domain.vo.resp.UserInfoResp;
import com.yan.yanchat.common.user.service.UserService;
import com.yan.yanchat.common.user.service.adapter.UserAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * @Author: sixcolor
 * @Date: 2024-02-15 16:33
 * @Description:
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserBackpackDao userBackpackDao;

    @Override
    @Transactional
    public Long register(User insert) {
        userDao.save(insert);
        return insert.getId();
    }

    @Override
    public UserInfoResp getUserInfo(Long uid) {
        User user = userDao.getById(uid);
        Integer modifyNameCount = userBackpackDao.getCountByValidItemId(uid, ItemEnum.MODIFY_NAME_CARD.getId());
        return UserAdapter.buildUserInfo(user,modifyNameCount);
    }

    @Override
    public void modifyName(Long uid, String name) {
        User user = userDao.getByName(name);
        
        userDao.modifyName(uid,name);
    }
}
