package com.yan.yanchat.common.infrastructure.interceptor;

import cn.hutool.extra.servlet.ServletUtil;
import com.yan.yanchat.common.infrastructure.domain.dto.RequestInfo;
import com.yan.yanchat.common.infrastructure.utils.RequestHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * @Author: sixcolor
 * @Date: 2024-02-16 15:09
 * @Description:
 */
@Component
public class CollectorInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Long uid = Optional.ofNullable(request.getAttribute(TokenInterceptor.UID))
                .map(Object::toString)
                .map(Long::parseLong).orElse(null);
        RequestInfo requestInfo = new RequestInfo();
        requestInfo.setIp(ServletUtil.getClientIP(request));
        requestInfo.setUid(uid);
        RequestHolder.set(requestInfo);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        RequestHolder.remove();
    }
}
