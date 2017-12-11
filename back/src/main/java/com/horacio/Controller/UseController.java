package com.horacio.Controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.horacio.Model.Admin;
import com.horacio.Model.Record;
import com.horacio.Repository.AdminRepository;
import com.horacio.Service.AdminService;
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
import java.util.Map;

/**
 * Created by arlex on 2017/12/1.
 */
@RestController
@RequestMapping(value = "/use")
public class UseController {

    @Autowired
    RecordService recordService;

    @Autowired
    AdminService adminService;



    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Object add(@RequestBody Map<String,Object> request, HttpSession session) throws Exception{
        String userid = (String)session.getAttribute("userid");
        String itemName = request.containsKey("itemName")?(String)request.get("itemName"):null;
        String borrowedTime = request.containsKey("borrowedTime")?(String)request.get("borrowedTime"):null;
        Integer number = request.containsKey("number")?(Integer)request.get("number"):null;
        Integer id = request.containsKey("adminid")?(Integer)request.get("adminid"):null;
        Admin admin = adminService.item(String.valueOf(id));
        String name = admin.getName();
        String phone = admin.getPhoneNumber();
        String email = admin.getEmail();
        recordService.add(itemName,borrowedTime,name,phone,email,number,1,userid);
        return ResultUtil.success();
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public Object edit(@RequestBody Map<String,Object> request,HttpSession session) throws Exception{
        String userid = (String)session.getAttribute("userid");
        Integer id = request.containsKey("id")?(Integer)request.get("id"):null;
        Integer adminid = request.containsKey("adminid")?(Integer)request.get("adminid"):null;
        Integer number = request.containsKey("number")?(Integer)request.get("number"):null;
        Admin admin = adminService.item(String.valueOf(adminid));
        String name = admin.getName();
        String phone = admin.getPhoneNumber();
        String email = admin.getEmail();
        recordService.edit(id,name,phone,email,userid,number);
        return ResultUtil.success();
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.POST)
    public Object register(@RequestBody Map<String,Object> request) throws Exception{
        String name = request.containsKey("name")?(String)request.get("name"):null;
        ArrayNode records = recordService.getAll(1,name);
        return ResultUtil.success(records);

    }

}
