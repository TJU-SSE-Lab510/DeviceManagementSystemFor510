package com.horacio.Controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.horacio.Model.Record;
import com.horacio.Service.RecordService;
import com.horacio.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
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
    public Object register(@RequestBody JsonNode body) throws Exception{
        recordService.add(body);
        return ResultUtil.success();

    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public Object edit(@RequestBody JsonNode body) throws Exception{
        recordService.edit(body);
        return ResultUtil.success();

    }

    @RequestMapping(value = "/returnItem", method = RequestMethod.POST)
    public Object returnItem(@RequestBody JsonNode body) throws Exception{
        recordService.returnItem(body);
        return ResultUtil.success();

    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public Object register() throws Exception{
        List<Record> records = recordService.getAll();
        return ResultUtil.success(records);

    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Object delete(@RequestBody JsonNode body) throws Exception{
        recordService.delete(body);
        return ResultUtil.success();

    }



}
