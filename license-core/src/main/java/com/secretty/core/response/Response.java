package com.secretty.core.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

@Data
public class Response<T> {

    /** 响应结果消息*/

    private Integer code;

    /** 响应结果消息*/
    private String message;

    /** 响应结果对应的（包含）的数据，空的话不反序列话*/
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private T data;

    /** 响应时间*/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date timeStamp = new Date();

    public static <T> Response<T> success(T data) {
        return setResponseResult(0, "success", data);
    }

    public static <T> Response<T> fail(int code, String message) {
        return setResponseResult(code, message, null);
    }

    public static <T> Response<T> fail(String message) {
        return setResponseResult(-1, message, null);
    }

    private static <T> Response<T> setResponseResult(int code, String message, T data) {
        Response<T> response = new Response<>();
        response.setCode(code);
        response.setMessage(message);
        response.setData(data);
        return response;
    }
}
