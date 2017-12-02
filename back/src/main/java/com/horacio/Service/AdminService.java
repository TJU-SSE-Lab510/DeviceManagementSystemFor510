package com.horacio.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.horacio.Enum.ResultEnum;
import com.horacio.Exception.LabsException;
import com.horacio.Model.Admin;
import com.horacio.Repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Horac on 2017/5/15.
 */
@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private ObjectMapper mapper;

    @Transactional
    public String login(JsonNode data) throws Exception{
        Admin user = adminRepository.findByUsername(data.get("username").textValue());
        if (user == null){
            throw new LabsException(ResultEnum.USER_NOT_FOUND.getCode(),ResultEnum.USER_NOT_FOUND.getMsg());
        }else {
            if(user.getPassword().equals(data.get("password").textValue())){
                return user.getName();
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
            System.out.println(data.get("name").textValue());
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
            user.setPassword(data.get("password").textValue());
            user.setName(data.get("name").textValue());
            user.setPhoneNumber(data.get("phoneNumber").textValue());
            adminRepository.save(user);
            return true;
        }

    }

    @Transactional
    public Boolean delete(JsonNode data) throws Exception{
        Admin user = adminRepository.findOne(data.get("id").intValue());
        if (user == null){
            throw new LabsException(ResultEnum.USER_NOT_FOUND.getCode(),ResultEnum.USER_NOT_FOUND.getMsg());
        }else {
            adminRepository.delete(user);
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
}
