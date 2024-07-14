package com.yan.yanchat.common.user.service.impl;

import com.yan.yanchat.common.user.dao.UserDao;
import com.yan.yanchat.common.user.domain.entity.User;
import com.yan.yanchat.common.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: sixcolor
 * @Date: 2024-02-15 16:33
 * @Description:
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    @Transactional
    public Long register(User insert) {
        userDao.save(insert);
        return insert.getId();
    }
}
