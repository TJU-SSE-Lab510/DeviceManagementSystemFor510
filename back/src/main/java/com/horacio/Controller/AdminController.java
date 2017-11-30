package com.horacio.Controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.horacio.Response.BaseResp;
import com.horacio.Service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Horac on 2017/5/15.
 */
@RestController
@RequestMapping(value = "/admin")
public class AdminController {

    @Autowired
    AdminService adminService;


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public BaseResp login(@RequestBody JsonNode body){
        String name = adminService.login(body);
        if(!name.equals(-1+"")) {
            Map<String, Object> data = new HashMap<>();
            data.put("token", name);
            return new BaseResp(BaseResp.SUCCESS, data);
        }
        else return new BaseResp(BaseResp.USERNAMEORPASSWORDERROR, null);

    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public BaseResp register(@RequestBody JsonNode body){
        if(adminService.register(body)){
            return new BaseResp(BaseResp.SUCCESS, null);
        }
        else return new BaseResp(BaseResp.DUPLICATEUSERNAME, null);

    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public BaseResp edit(@RequestBody JsonNode body){
        if(adminService.edit(body)){
            return new BaseResp(BaseResp.SUCCESS, null);
        }
        else return new BaseResp(BaseResp.NOUSER, null);

    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public BaseResp register(){
        return new BaseResp(BaseResp.SUCCESS,adminService.getAll());

    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public BaseResp delete(@RequestBody JsonNode body){
        if(adminService.delete(body)){
            return new BaseResp(BaseResp.SUCCESS, null);
        }
        else return new BaseResp(BaseResp.NOUSER, null);

    }

}
