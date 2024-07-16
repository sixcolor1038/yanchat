package com.yan.yanchat.common.infrastructure.interceptor;

import com.yan.yanchat.common.infrastructure.exception.HttpErrorEnum;
import com.yan.yanchat.common.user.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;
import java.util.Optional;

/**
 * @Author: sixcolor
 * @Date: 2024-02-16 14:31
 * @Description:
 */
@Component
public class TokenInterceptor implements HandlerInterceptor {

    @Autowired
    private LoginService loginService;
    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String AUTHORIZATION_SCHEMA = "Bearer ";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = getToken(request);
        Long uid = loginService.getValidUid(token);
        if (Objects.nonNull(uid)) {
            //用户有登录态
            request.setAttribute("uid", uid);
        } else {
            //用户未登录
            boolean isPublicURI = isPublicURI(request);
            if (!isPublicURI) {
                HttpErrorEnum.ACCESS_DENIED.sendHttpError(response);
                return false;
            }
        }
        return true;
    }


    private static boolean isPublicURI(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        String[] split = requestURI.split("/");
        return split.length > 3 && "public".equals(split[3]);
    }

    private String getToken(HttpServletRequest request) {
        String header = request.getHeader(HEADER_AUTHORIZATION);
        return Optional.ofNullable(header)
                .filter(x -> x.startsWith(AUTHORIZATION_SCHEMA))
                .map(x -> x.replaceFirst(AUTHORIZATION_SCHEMA, ""))
                .orElse(null);
    }
}
