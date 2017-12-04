package com.horacio.utils;

import com.altynai.checkcar.enums.CarEnum;
import com.altynai.checkcar.enums.ResultEnum;
import com.altynai.checkcar.exception.CheckCarException;
import com.horacio.Enum.ResultEnum;
import com.horacio.Exception.LabsException;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

public class AuthCheckUtil {

    /**
     * 检查权限
     * @return
     * @throws Exception
     */
    public static void check(HttpSession session) throws Exception{
        String superuser = (String)session.getAttribute("superuser");
        if(!superuser.equals("1")){
            throw new LabsException(ResultEnum.AUTHCAR_NOT_FOUND.getCode(),ResultEnum.AUTHCAR_NOT_FOUND.getMsg());
        }
    }

}
