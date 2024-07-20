package com.yan.yanchat.common.websocket.domain.vo.resp.ws;

import lombok.*;

/**
 * @Author: sixcolor
 * @Date: 2024-02-14
 * @Description:
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WSLoginSuccess {
    private Long uid;
    private String avatar;
    private String token;
    private String name;
    //用户权限 0普通用户 1超管
    private Integer power;
}
