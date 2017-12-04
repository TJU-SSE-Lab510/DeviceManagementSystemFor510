package com.horacio.Controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.horacio.Model.Record;
import com.horacio.Service.RecordService;
import com.horacio.utils.AuthCheckUtil;
import com.horacio.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by arlex on 2017/12/1.
 */
@RestController
@RequestMapping(value = "/record")
public class RecordController {

    @Autowired
    RecordService recordService;


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Object add(@RequestBody JsonNode body,HttpSession session) throws Exception{
        String userid = (String)session.getAttribute("userid");
        recordService.add(body,userid);
        return ResultUtil.success();
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public Object edit(@RequestBody JsonNode body,HttpSession session) throws Exception{
        String userid = (String)session.getAttribute("userid");
        recordService.edit(body,userid);
        return ResultUtil.success();

    }

    @RequestMapping(value = "/returnItem", method = RequestMethod.POST)
    public Object returnItem(@RequestBody JsonNode body,HttpSession session) throws Exception{
        String userid = (String)session.getAttribute("userid");
        recordService.returnItem(body,userid);
        return ResultUtil.success();

    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public Object register() throws Exception{
        List<Record> records = recordService.getAll();
        return ResultUtil.success(records);

    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Object delete(@RequestBody JsonNode body,HttpSession session) throws Exception{
        AuthCheckUtil.check(session);
        recordService.delete(body);
        return ResultUtil.success();

    }

}
