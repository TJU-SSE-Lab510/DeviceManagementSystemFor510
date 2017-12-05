package com.horacio.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.horacio.Enum.ResultEnum;
import com.horacio.Exception.LabsException;
import com.horacio.Model.Facility;
import com.horacio.Properties.FileProperties;
import com.horacio.Repository.FacilityRepository;
import com.horacio.utils.UploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by arlex on 2017/12/1.
 */
@Service
public class FacilityService {

    @Autowired
    private FacilityRepository facilityRepository;

    @Autowired
    private FileProperties fileProperties;


    @Autowired
    private ObjectMapper mapper;



    @Transactional
    public Boolean add(JsonNode data) throws Exception{
        Facility item = new Facility();
        item.setItemName(data.get("itemName").textValue());
        item.setDate(new Date(Long.valueOf(data.get("date").textValue())));
        item.setItemQTY(data.get("itemQTY").intValue());
        item.setUrl(data.get("url").textValue());
        item.setRemainNum(data.get("itemQTY").intValue());
        facilityRepository.save(item);
        return true;
    }

    @Transactional
    public Boolean edit(JsonNode data) throws Exception{
        Facility item =facilityRepository.findOne(data.get("id").intValue());
        if(item == null){
            throw new LabsException(ResultEnum.OBJECT_NOT_FOUND.getCode(),ResultEnum.OBJECT_NOT_FOUND.getMsg());
        }
        int newNum = data.get("itemQTY").intValue();
        int origin  = item.getItemQTY();
        int change_num = newNum - origin;
        int remain_num = item.getRemainNum()+change_num;
        if(remain_num < 0){
            throw new LabsException(ResultEnum.FACILITY_NOT_ENOUGH.getCode(),ResultEnum.FACILITY_NOT_ENOUGH.getMsg());
        }
        item.setItemQTY(newNum);
        item.setRemainNum(remain_num);
        item.setUrl(data.get("url").textValue());
        facilityRepository.save(item);
        return true;
    }


    @Transactional
    public ArrayNode getAll() throws Exception{
        List<Facility> items = facilityRepository.findAll();
        ArrayNode array = mapper.createArrayNode();
        for(Facility item: items){
            ObjectNode node = mapper.createObjectNode();
            node.put("id",item.getId());
            node.put("itemName",item.getItemName());
            node.put("itemQTY",item.getItemQTY());
            node.put("remainNum",item.getRemainNum());
            node.put("url",fileProperties.getFacilityUrl()+"/"+item.getUrl());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = String.valueOf(sdf.parse(item.getDate().toString()).getTime());
            node.put("date",time);
            array.addPOJO(node);
        }
        return array;
    }

    @Transactional
    public Boolean delete(JsonNode data) throws Exception{
        Facility item = facilityRepository.findOne(data.get("id").intValue());
        if (item == null){
            throw new LabsException(ResultEnum.OBJECT_NOT_FOUND.getCode(),ResultEnum.OBJECT_NOT_FOUND.getMsg());
        }else {
            facilityRepository.delete(item);
            String url = fileProperties.getImagePath()+UploadUtil.separatar+item.getUrl();
            File image = new File(url);
            if(image != null){
                image.delete();
            }
            return true;
        }

    }


    /**
     * 上传设备图片
     * @param stream
     * @param contentType
     * @return
     * @throws Exception
     */
    public String uploadFacilityImage(InputStream stream, String contentType) throws Exception{
        String name = UploadUtil.generatorName();
        String out = fileProperties.getFacilityPath()+UploadUtil.separatar+name+"."+contentType;
        UploadUtil.upload(stream,out);
        String link = fileProperties.getFacilityUrl()+"/"+name+"."+contentType;
        return link;
    }

}
