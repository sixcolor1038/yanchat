package com.yan.yanchat.common.user.domain.vo.resp.friend;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: sixcolor
 * @Date: 2024-07-20
 * @Description: 好友申请未读数
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FriendUnreadResp {

    @ApiModelProperty("申请列表的未读数")
    private Integer unReadCount;

}
