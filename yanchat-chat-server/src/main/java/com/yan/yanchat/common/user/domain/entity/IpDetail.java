package com.yan.yanchat.common.user.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: sixcolor
 * @Date: 2024-02-18 11:15
 * @Description: ip详情
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IpDetail {
    private static final long serialVersionUID = 1L;
    //注册时的ip
    private String ip;
    //最新登录的ip
    private String isp;
    private String isp_id;
    private String city;
    private String city_id;
    private String country;
    private String country_id;
    private String region;
    private String region_id;
}
