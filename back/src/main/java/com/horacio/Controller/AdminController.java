package com.horacio.Controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.horacio.Enum.ResultEnum;
import com.horacio.Exception.LabsException;
import com.horacio.Model.Admin;
import com.horacio.Service.AdminService;
import com.horacio.utils.AuthCheckUtil;
import com.horacio.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;
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
    public Object login(@RequestBody Map<String,Object> request, HttpServletResponse httpresponse) throws Exception{
        ObjectNode result = adminService.login(request);
        //设置Cookie
        String userid = URLEncoder.encode(result.get("userid").toString(),"UTF-8");
        httpresponse.addHeader("Set-Cookie", "token="+userid+"; Max-Age=259200;Path=/");
        String superuser = URLEncoder.encode(result.get("superuser").toString(),"UTF-8");
        httpresponse.addHeader("Set-Cookie", "superuser="+superuser+"; Max-Age=259200;Path=/");
        return ResultUtil.success(result);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Object register(@RequestBody JsonNode body,HttpSession session) throws Exception{
        AuthCheckUtil.check(session);
        adminService.register(body);
        return ResultUtil.success();
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public Object edit(@RequestBody JsonNode body,HttpSession session) throws Exception{
        AuthCheckUtil.check(session);
        adminService.edit(body);
        return ResultUtil.success();
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public Object register()throws Exception {
        ArrayNode list = adminService.getAll();
        return ResultUtil.success(list);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Object delete(@RequestBody JsonNode body,HttpSession session)throws Exception{
        AuthCheckUtil.check(session);
        adminService.delete(body);
        return ResultUtil.success();
    }

}
