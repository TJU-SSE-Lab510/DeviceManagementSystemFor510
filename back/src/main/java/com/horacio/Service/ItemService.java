package com.horacio.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.horacio.Model.Item;
import com.horacio.Repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Decoder;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by Horac on 2017/5/15.
 */
@Service
public class ItemService {

    @Autowired
    private ItemRepository itemrepository;




    @Transactional
    public Boolean register(JsonNode data){

            Item item = new Item();
            item.setItemName(data.get("itemName").textValue());
            item.setDate(data.get("date").intValue());
            item.setItemQTY(data.get("itemQTY").intValue());
            item.setUrl(data.get("url").textValue());
            itemrepository.save(item);
            return true;
    }

    @Transactional
    public Boolean edit(JsonNode data){
        Item item =itemrepository.findOne(data.get("id").intValue());
        item.setItemQTY(data.get("itemQTY").intValue());
        item.setUrl(data.get("url").textValue());
        itemrepository.save(item);
        return true;

    }


    @Transactional
    public List<Item> getAll(){
        List<Item> items = itemrepository.findAll();
        return  items;
    }

    @Transactional
    public Boolean delete(JsonNode data){
        Item item = itemrepository.findOne(data.get("id").intValue());
        if (item == null){
            return false;
        }else {
            itemrepository.delete(item);
            return true;
        }

    }

    public static boolean generateImage(String imgStr, String path) {
        if (imgStr == null)
            return false;
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            // 解密
             byte[] b = decoder.decodeBuffer(imgStr);
            // 处理数据
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }

            System.out.println(path);
            OutputStream out = new FileOutputStream(path);
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Transactional
    public String upload(JsonNode data){
        File directory = new File(".");
        String path = null;
        path +="C:\\Program Files\\Apache Software Foundation\\Tomcat 8.5\\webapps\\devicePic";
        path = path+"\\"+System.currentTimeMillis()+".png";
        if(generateImage(data.get("base64").textValue(),path)){
            return path;
        }
        else return "";
    }

}
