package com.horacio.utils;

import com.horacio.Enum.ResultEnum;
import com.horacio.Response.Result;

public class ResultUtil {

    /**
     * 接口调用成功的方法（有对象返回）
     * @param object
     * @return
     */
    public static Result success(Object object){
        Result result = new Result();
        result.setErrCode(ResultEnum.SUCCESS.getCode());
        result.setErrMsg(ResultEnum.SUCCESS.getMsg());
        result.setData(object);
        return result;
    }

    /**
     * 接口调用成功的方法(没有对象返回)
     * @return
     */
    public static Result success(){
        Result result = new Result();
        result.setErrCode(ResultEnum.SUCCESS.getCode());
        result.setErrMsg(ResultEnum.SUCCESS.getMsg());
        return result;
    }

    /**
     * 接口调用失败的方法
     * @param code
     * @param msg
     * @return
     */
    public static Result error(Integer code,String msg){
        Result result = new Result();
        result.setErrCode(code);
        result.setErrMsg(msg);
        return result;
    }
}
