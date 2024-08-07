package com.yan.yanchat.common.infrastructure.domain.vo.resp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yan.yanchat.common.infrastructure.exception.ErrorEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: sixcolor
 * @Date: 2024-02-16
 * @Description: 通用返回体
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel("基础返回体")
public class ApiResult<T> {
    @ApiModelProperty("成功标识true or false")
    private Boolean success;
    @ApiModelProperty("错误码")
    private Integer errCode;
    @ApiModelProperty("错误消息")
    private String errMsg;
    @ApiModelProperty("返回对象")
    private T data;

    public static <T> ApiResult<T> success() {
        ApiResult<T> result = new ApiResult<T>();
        result.setData(null);
        result.setSuccess(Boolean.TRUE);
        return result;
    }

    public static <T> ApiResult<T> success(T data) {
        ApiResult<T> result = new ApiResult<T>();
        result.setData(data);
        result.setSuccess(Boolean.TRUE);
        return result;
    }

    public static <T> ApiResult<T> fail(Integer code, String msg) {
        ApiResult<T> result = new ApiResult<T>();
        result.setSuccess(Boolean.FALSE);
        result.setErrCode(code);
        result.setErrMsg(msg);
        return result;
    }

    public static <T> ApiResult<T> fail(ErrorEnum e) {
        ApiResult<T> result = new ApiResult<T>();
        result.setSuccess(Boolean.FALSE);
        result.setErrCode(e.getErrorCode());
        result.setErrMsg(e.getErrorMsg());
        return result;
    }

    public boolean isSuccess() {
        return this.success;
    }
}
