package com.yan.yanchat.common.infrastructure.exception;

import com.yan.yanchat.common.infrastructure.domain.vo.resp.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author: sixcolor
 * @Date: 2024-02-16 20:50
 * @Description: 捕获全局异常
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * validation参数校验异常
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ApiResult<?> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        StringBuilder errorMsg = new StringBuilder();
        e.getBindingResult().getFieldErrors().forEach(x -> errorMsg.append(x.getField()).append(x.getDefaultMessage()));
        String msg = errorMsg.toString();
        return ApiResult.fail(CommonErrorEnum.PARAM_INVALID.getCode(), msg.substring(0, msg.length() - 1));
    }

    /**
     *
     */
    @ExceptionHandler(value = Throwable.class)
    public ApiResult<?> throwable(Throwable e){
        log.error("system exception！ the reason is :{}",e.getMessage());
        return ApiResult.fail(CommonErrorEnum.SYSTEM_ERROR);
    }

}
