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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.InputStream;
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
//        String userid = URLEncoder.encode(result.get("userid").toString(),"UTF-8");
//        httpresponse.addHeader("Set-Cookie", "token="+userid+"; Max-Age=259200;Path=/");
//        String superuser = URLEncoder.encode(result.get("superuser").toString(),"UTF-8");
//        httpresponse.addHeader("Set-Cookie", "superuser="+superuser+"; Max-Age=259200;Path=/");
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

    //用户具体信息
    @RequestMapping(value = "/item", method = RequestMethod.GET)
    public Object item(HttpSession session) throws Exception{
        String userid = (String)session.getAttribute("userid");
        Admin user = adminService.item(userid);
        return ResultUtil.success(user);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Object update(@RequestBody JsonNode body) throws Exception{
        adminService.update(body);
        return ResultUtil.success();
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public Object getAll()throws Exception {
        ArrayNode list = adminService.getAll();
        return ResultUtil.success(list);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Object delete(@RequestBody JsonNode body,HttpSession session)throws Exception{
        AuthCheckUtil.check(session);
        adminService.delete(body);
        return ResultUtil.success();
    }

    @PostMapping(value = "/upload")
    public Object upload(@RequestParam("file") MultipartFile picture, HttpSession session) throws Exception{
        if(picture.isEmpty()){
            throw new LabsException(ResultEnum.PARAM_NOT_FOUND.getCode(),ResultEnum.PARAM_NOT_FOUND.getMsg());
        }
        //getContentType返回的是image/png...
        if(!picture.getContentType().startsWith("image")){
            throw new LabsException(ResultEnum.FILE_TYPE_ERROR.getCode(), ResultEnum.FILE_TYPE_ERROR.getMsg());
        }
        // getSize 函数返回的是字节数
        if(picture.getSize()>20*1024*1024){
            throw new LabsException(ResultEnum.FILE_SIZE_ERROR.getCode(),ResultEnum.FILE_SIZE_ERROR.getMsg());
        }
        String userid = (String)session.getAttribute("userid");
        InputStream file = picture.getInputStream();
        String contentType = picture.getContentType().split("/")[1];
        String filePath = adminService.uploadUserImage(file,contentType,userid);
        return ResultUtil.success(filePath);
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Object search(@RequestBody Map<String,Object> request)throws Exception {
        String studentNumber = request.containsKey("studentNumber")?(String)request.get("studentNumber"):null;
        ArrayNode list = adminService.search(studentNumber);
        return ResultUtil.success(list);
    }

}
