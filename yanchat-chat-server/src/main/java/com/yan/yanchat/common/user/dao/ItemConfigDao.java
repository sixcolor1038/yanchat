package com.yan.yanchat.common.user.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yan.yanchat.common.user.domain.entity.ItemConfig;
import com.yan.yanchat.common.user.mapper.ItemConfigMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: sixcolor
 * @Date: 2024-02-16
 * @Description: 功能物品配置表 服务实现类
 */
@Service
public class ItemConfigDao extends ServiceImpl<ItemConfigMapper, ItemConfig> {

    public List<ItemConfig> getByType(Integer type) {
        return lambdaQuery()
                .eq(ItemConfig::getType, type)
                .list();
    }
}
