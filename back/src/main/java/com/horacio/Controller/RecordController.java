package com.horacio.Controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.horacio.Enum.ResultEnum;
import com.horacio.Exception.LabsException;
import com.horacio.Model.Record;
import com.horacio.Service.RecordService;
import com.horacio.utils.AuthCheckUtil;
import com.horacio.utils.ResultUtil;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * Created by arlex on 2017/12/8.
 */
@RestController
@RequestMapping(value = "/record")
public class RecordController {

    @Autowired
    RecordService recordService;


    @RequestMapping(value = "/returnItem", method = RequestMethod.POST)
    public Object returnItem(@RequestBody Map<String,Object> request, HttpSession session) throws Exception{
        Integer id = request.containsKey("id")?(Integer)request.get("id"):null;
        String returnTime = request.containsKey("returnTime")?(String)request.get("returnTime"):null;
        Integer number = request.containsKey("number")?(Integer)request.get("number"):null;
        if(number<=0){
            throw new LabsException(ResultEnum.INPUT_ILLEGAL.getCode(),ResultEnum.INPUT_ILLEGAL.getMsg());
        }
        String userid = (String)session.getAttribute("userid");
        recordService.returnItem(id,returnTime,userid,number);
        return ResultUtil.success();

    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Object delete(@RequestBody JsonNode body,HttpSession session) throws Exception{
        AuthCheckUtil.check(session);
        recordService.delete(body);
        return ResultUtil.success();

    }

}
