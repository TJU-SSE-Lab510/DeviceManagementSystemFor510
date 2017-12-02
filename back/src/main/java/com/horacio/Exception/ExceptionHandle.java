package com.horacio.Exception;

import com.horacio.Enum.ResultEnum;
import com.horacio.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionHandle {
    final static Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Object handle(Exception e){
        if(e instanceof LabsException){
            LabsException labsException = (LabsException) e;
            return ResultUtil.error(labsException.getCode(),labsException.getMessage());
        }else{
            e.printStackTrace();
            return ResultUtil.error(ResultEnum.UNKNOW_ERROR.getCode(),ResultEnum.UNKNOW_ERROR.getMsg());
        }
    }
}
