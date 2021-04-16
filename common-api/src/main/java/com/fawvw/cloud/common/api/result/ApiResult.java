package com.fawvw.cloud.common.api.result;

import lombok.Data;

import java.io.Serializable;

/**
 * @author HuangGang
 * @create 2021-01-27 14:35
 **/
@Data
public class ApiResult<T> implements Serializable {
    /**
     * 请求是否成功
     */
    private boolean success;
    /**
     * 返回的的数据
     */
    private T data;
    /**
     * 错误代码
     */
    private String errorCode;
    /**
     * 错误消息
     */
    private String errorMsg;

    /**
     * 成功(不带数据)
     * @param <T>
     * @return
     */
    public static <T> ApiResult<T> success() {
        return new ApiResult<T>(true, null, "", "");
    }
    /**
     * 成功(带数据)
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ApiResult<T> success(T data) {
        return new ApiResult<T>(true, data, "", "");
    }

    /**
     * 失败(带数据)
     * @param errorCode
     * @param errorMsg
     * @param <T>
     * @return
     */
    public static <T> ApiResult<T> error(String errorCode, String errorMsg) {
        return new ApiResult<T>(false, null, errorCode, errorMsg);
    }

    public ApiResult(){}

    public ApiResult(boolean success, T data, String errorCode, String errorMsg) {
        this.success = success;
        this.data = data;
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

}
