package com.horacio.Controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.horacio.Model.Record;
import com.horacio.Response.BaseResp;
import com.horacio.Service.RecordService;
import jdk.nashorn.internal.runtime.Debug;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Horac on 2017/5/15.
 */
@RestController
@RequestMapping(value = "/user")
public class RecordController {

    @Autowired
    RecordService recordService;


    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public BaseResp register(@RequestBody JsonNode body){
        if(recordService.register(body)){
            return new BaseResp(BaseResp.SUCCESS, null);
        }
        else return new BaseResp(BaseResp.DUPLICATEUSERNAME, null);

    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public BaseResp edit(@RequestBody JsonNode body){
        if(recordService.edit(body)){
            return new BaseResp(BaseResp.SUCCESS, null);
        }
        else return new BaseResp(BaseResp.NOUSER, null);

    }

    @RequestMapping(value = "/returnItem", method = RequestMethod.POST)
    public BaseResp returnItem(@RequestBody JsonNode body){
        if(recordService.returnItem(body)){
            return new BaseResp(BaseResp.SUCCESS, null);
        }
        else return new BaseResp(BaseResp.NOUSER, null);

    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public BaseResp register(){
        return new BaseResp(BaseResp.SUCCESS,recordService.getAll());

    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public BaseResp delete(@RequestBody JsonNode body){
        if(recordService.delete(body)){
            return new BaseResp(BaseResp.SUCCESS, null);
        }
        else return new BaseResp(BaseResp.NOUSER, null);

    }



}
