package com.horacio.Controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.horacio.Response.BaseResp;
import com.horacio.Service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Horac on 2017/5/15.
 */
@RestController
@RequestMapping(value = "/item")
public class ItemController {

    @Autowired
    ItemService itemService;


    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public BaseResp register(@RequestBody JsonNode body){
        if(itemService.register(body)){
            return new BaseResp(BaseResp.SUCCESS, null);
        }
        else return new BaseResp(BaseResp.DUPLICATEUSERNAME, null);

    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public BaseResp edit(@RequestBody JsonNode body){
        if(itemService.edit(body)){
            return new BaseResp(BaseResp.SUCCESS, null);
        }
        else return new BaseResp(BaseResp.NOUSER, null);

    }


    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public BaseResp register(){
        return new BaseResp(BaseResp.SUCCESS,itemService.getAll());

    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public BaseResp delete(@RequestBody JsonNode body){
        if(itemService.delete(body)){
            return new BaseResp(BaseResp.SUCCESS, null);
        }
        else return new BaseResp(BaseResp.NOUSER, null);

    }


    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public BaseResp upload(@RequestBody JsonNode body){
        return new BaseResp(BaseResp.SUCCESS,itemService.upload(body));

    }

}
