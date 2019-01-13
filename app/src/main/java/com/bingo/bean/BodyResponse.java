package com.bingo.bean;

/**
 * Created by bingo on 2018/12/28.
 * Time:2018/12/28
 */

public class BodyResponse<T> {
    public final static int CODE_SUCCESS = 200;//成功
    public final static int CODE_FAIL = 400;//失败
    public final static int CODE_UNAUTHORIZED = 401;//未认证（签名错误）
    public final static int CODE_NOT_FOUND = 404;//接口不存在
    public final static int CODE_INTERNAL_SERVER_ERROR = 500;//服务器内部错误

    public int code;
    public String msg;
    public T data;

    @Override
    public String toString() {
        return "BodyResponse{" +
                "code='" + code + '\'' +
                ", message='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
