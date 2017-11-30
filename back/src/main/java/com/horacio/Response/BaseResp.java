package com.horacio.Response;


/**
 * 统一的JSON回复格式
 * {
 * errCode: int, // 错误码，具体含义请查表
 * data: obj/array // JSON对象或者JSON数组
 * }
 */
public class BaseResp {

    public static final int SUCCESS = 0;
    public static final int UNKNOWN = -1;
    public static final int USERNAMEORPASSWORDERROR = 100; //用户名或密码错误
    public static final int DUPLICATEUSERNAME = 101; //用户名重复
    public static final int NOUSER = 102; //找不到用户

    public int errCode;
    public Object data;

    public BaseResp(int errCode) {
        this.errCode = errCode;
    }

    public BaseResp(int errCode, Object data) {
        this.errCode = errCode;
        this.data = data;
    }
}