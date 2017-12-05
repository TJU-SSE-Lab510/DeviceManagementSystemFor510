package com.horacio.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.horacio.Enum.ResultEnum;
import com.horacio.Exception.LabsException;
import com.horacio.Model.Admin;
import com.horacio.Properties.FileProperties;
import com.horacio.Repository.AdminRepository;
import com.horacio.utils.UploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Horac on 2017/5/15.
 */
@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private FileProperties fileProperties;

    @Transactional
    public ObjectNode login( Map<String,Object> data) throws Exception{
        Admin user = adminRepository.findByUsername((String)data.get("username"));
        if (user == null){
            throw new LabsException(ResultEnum.USER_NOT_FOUND.getCode(),ResultEnum.USER_NOT_FOUND.getMsg());
        }else {
            if(user.getPassword().equals((String)data.get("password"))){
                ObjectNode node = mapper.createObjectNode();
                node.put("userid",user.getId());
                node.put("token",user.getName());
                node.put("superuser",user.getSuperuser());
                node.put("url",fileProperties.getUserUrl()+"/"+user.getUrl());
                return node;
            }
            else{
                throw new LabsException(ResultEnum.USER_PASSWORD_ERROR.getCode(),ResultEnum.USER_PASSWORD_ERROR.getMsg());
            }
        }

    }

    @Transactional
    public Boolean register(JsonNode data) throws Exception{
        Admin user = adminRepository.findByUsername(data.get("username").textValue());
        if (user != null){
            throw new LabsException(ResultEnum.USER_ALREADY_EXIST.getCode(),ResultEnum.USER_ALREADY_EXIST.getMsg());
        }else {
            user = new Admin();
            user.setUsername(data.get("username").textValue());
            user.setPassword(data.get("password").textValue());
            user.setName(data.get("name").textValue());
            user.setSuperuser(data.get("superuser").intValue());
            user.setPhoneNumber(data.get("phoneNumber").textValue());
            adminRepository.save(user);
            return true;
            }

    }

    @Transactional
    public Boolean edit(JsonNode data) throws Exception{
        Admin user = adminRepository.findByUsername(data.get("username").textValue());
        if (user == null){
            throw new LabsException(ResultEnum.USER_NOT_FOUND.getCode(),ResultEnum.USER_NOT_FOUND.getMsg());
        }else {
            if(data.get("password") != null && !data.get("password").textValue().equals("")){
                user.setPassword(data.get("password").textValue());
            }
            user.setName(data.get("name").textValue());
            user.setPhoneNumber(data.get("phoneNumber").textValue());
            user.setSuperuser(data.get("superuser").intValue());
            adminRepository.save(user);
            return true;
        }
    }

    @Transactional
    public Boolean update(JsonNode data) throws Exception{
        Admin user = adminRepository.findByUsername(data.get("username").textValue());
        if (user == null){
            throw new LabsException(ResultEnum.USER_NOT_FOUND.getCode(),ResultEnum.USER_NOT_FOUND.getMsg());
        }else {
            user.setPassword(data.get("password").textValue());
            user.setName(data.get("name").textValue());
            user.setPhoneNumber(data.get("phoneNumber").textValue());
            adminRepository.save(user);
            return true;
        }
    }

    @Transactional
    public Admin item(String userid) throws Exception{
        Admin user = adminRepository.findOne(Integer.valueOf(userid));
        if (user == null){
            throw new LabsException(ResultEnum.USER_NOT_FOUND.getCode(),ResultEnum.USER_NOT_FOUND.getMsg());
        }else {
            return user;
        }
    }

    @Transactional
    public Boolean delete(JsonNode data) throws Exception{
        Admin user = adminRepository.findOne(data.get("id").intValue());
        if (user == null){
            throw new LabsException(ResultEnum.USER_NOT_FOUND.getCode(),ResultEnum.USER_NOT_FOUND.getMsg());
        }else {
            String imageName = fileProperties.getUserPath()+UploadUtil.separatar+user.getUrl();
            adminRepository.delete(user);
            if (imageName.equals("default.png")){
                return true;
            }
            try{
                File image = new File(imageName);
                image.delete();
            }catch (Exception e){
                return true;
            }
            return true;
        }
    }


    @Transactional
    public ArrayNode getAll() throws Exception{
        List<Admin> admins = adminRepository.findAll();
        ArrayNode array = mapper.createArrayNode();
        for(Admin user: admins){
            ObjectNode node = mapper.createObjectNode();
            node.put("id",user.getId());
            node.put("username",user.getUsername());
            node.put("name",user.getName());
            node.put("phoneNumber",user.getPhoneNumber());
            array.addPOJO(node);
        }
        return array;
    }

    /**
     * 上传用户头像
     * @param stream
     * @param contentType
     * @return
     * @throws Exception
     */
    public String uploadUserImage(InputStream stream, String contentType,String userid) throws Exception{
        Admin user = adminRepository.findOne(Integer.valueOf(userid));
        if (user == null){
            throw new LabsException(ResultEnum.USER_NOT_FOUND.getCode(),ResultEnum.USER_NOT_FOUND.getMsg());
        }else {
            String name = UploadUtil.generatorName();
            String out = fileProperties.getUserPath() + UploadUtil.separatar + name + "." + contentType;
            UploadUtil.upload(stream, out);
            user.setUrl(name + "." + contentType);
            String link = fileProperties.getUserUrl() +"/"+ name + "." + contentType;
            return link;
        }
    }
}
