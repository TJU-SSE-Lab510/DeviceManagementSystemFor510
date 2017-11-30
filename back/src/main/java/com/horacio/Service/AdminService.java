package com.horacio.Service;

import com.fasterxml.jackson.databind.JsonNode;
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

    @Transactional
    public String login(JsonNode data){
        Admin user = adminRepository.findByUsername(data.get("username").textValue());
        if (user == null){
            return "-1";
        }else {
            if(user.getPassword().equals(data.get("password").textValue())){
                return user.getName();
            }
            else{
                return "-1";
            }
        }

    }

    @Transactional
    public Boolean register(JsonNode data){
        Admin user = adminRepository.findByUsername(data.get("username").textValue());
        if (user != null){
            return false;
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
    public Boolean edit(JsonNode data){
        Admin user = adminRepository.findByUsername(data.get("username").textValue());
        if (user == null){
            return false;
        }else {
            user.setPassword(data.get("password").textValue());
            user.setName(data.get("name").textValue());

            user.setPhoneNumber(data.get("phoneNumber").textValue());
            adminRepository.save(user);
            return true;
        }

    }

    @Transactional
    public Boolean delete(JsonNode data){
        Admin user = adminRepository.findOne(data.get("id").intValue());
        if (user == null){
            return false;
        }else {
            adminRepository.delete(user);
            return true;
        }

    }


    @Transactional
    public List<Admin> getAll(){
        List<Admin> admins = adminRepository.findAll();
        return  admins;
    }
}
