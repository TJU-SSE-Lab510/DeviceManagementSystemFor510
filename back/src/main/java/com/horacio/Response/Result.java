package com.horacio.Response;


/**
 * 接口返回的统一格式
 * @param <T>
 */
public class Result<T> {

//    错误码
    private Integer errCode;

//    错误信息
    private String errMsg;

//    返回对象
    private T data;


    public Integer getErrCode() {
        return errCode;
    }

    public void setErrCode(Integer errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
