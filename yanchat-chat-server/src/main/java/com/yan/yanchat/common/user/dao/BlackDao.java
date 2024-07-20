package com.yan.yanchat.common.user.dao;

import com.yan.yanchat.common.user.domain.entity.Black;
import com.yan.yanchat.common.user.mapper.BlackMapper;
import com.yan.yanchat.common.user.service.BlackService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @Author: sixcolor
 * @Date: 2024-02-18
 * @Description: 黑名单 服务实现类
 */
@Service
public class BlackDao extends ServiceImpl<BlackMapper, Black>{

}
