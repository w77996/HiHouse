package com.w77996.seed.house.core.result;

/**
 * 响应码枚举，参考HTTP状态码的语义
 * @author w77996
 */
public enum ResultCode {
    //成功
    SUCCESS(200),
    //失败
    FAIL(400),
    BUSY(405),
    UNAUTHORIZED(401),//未认证（签名错误）
    FORBIDDEN(403),//禁止访问（签名错误）
    NOT_FOUND(404),//接口不存在
    INTERNAL_SERVER_ERROR(500);//服务器内部错误

    private final int code;

    ResultCode(int code) {
        this.code = code;
    }

    public int code() {
        return code;
    }
}
