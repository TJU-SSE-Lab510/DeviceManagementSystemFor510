package com.horacio.Enum;

public enum ResultEnum {
    UNKNOW_ERROR(-1,"未知错误"),
    SUCCESS(0,"成功"),

    USER_NOT_FOUND(401,"用户名不存在"),
    USER_VERFIY_ERROR(402,"用户认证失败，请重新登录"),
    USER_PASSWORD_ERROR(403,"密码错误"),
    EMAIL_FORMAT_ERROR(405,"邮箱格式错误"),
    AUTH_NOT_FOUND(405,"您的权限不足"),
    USER_ALREADY_EXIST(406,"用户ID已存在"),


    OBJECT_NOT_FOUND(501,"对象不存在"),
    PARAM_NOT_FOUND(502,"参数缺失"),
    FACILITY_NOT_ENOUGH(503,"设备库存不足"),

    FILE_NOT_FOUND(601,"文件不存在"),
    FILE_TYPE_ERROR(602,"文件格式错误"),
    FILE_SIZE_ERROR(603,"文件过大"),
    FILE_UPLOAD_FAILED(604,"文件上传失败"),
    FILE_FOLDER_NOT_FOUND(606,"文件夹不存在"),

    OPERATE_NOT_ALLOW(701,"操作请求不合法"),
    STATUS_NOT_AVAILABLE(702,"操作业务状态不合法"),
    INPUT_ILLEGAL(703,"输入参数不合法"),
    ALREADY_RETURN(704,"设备已经归还"),
    ;


    private Integer code;

    private String msg;


    private ResultEnum(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }


    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
