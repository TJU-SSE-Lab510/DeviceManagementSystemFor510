package com.horacio.Controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.horacio.Model.Admin;
import com.horacio.Service.AdminService;
import com.horacio.utils.ResultUtil;
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
@RequestMapping(value = "/admin")
public class AdminController {

    @Autowired
    AdminService adminService;


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Object login(@RequestBody JsonNode body) throws Exception{
        String name = adminService.login(body);
        Map<String, Object> data = new HashMap<>();
        data.put("token", name);
        return ResultUtil.success(data);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Object register(@RequestBody JsonNode body) throws Exception{
        adminService.register(body);
        return ResultUtil.success();
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public Object edit(@RequestBody JsonNode body) throws Exception{
        adminService.edit(body);
        return ResultUtil.success();
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public Object register()throws Exception {
        List<Admin> list = adminService.getAll();
        return ResultUtil.success(list);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Object delete(@RequestBody JsonNode body)throws Exception{
        adminService.delete(body);
        return ResultUtil.success();

    }

}
