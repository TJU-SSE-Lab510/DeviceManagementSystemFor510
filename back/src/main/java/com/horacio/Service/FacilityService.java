package com.horacio.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.horacio.Model.Facility;
import com.horacio.Properties.FileProperties;
import com.horacio.Repository.FacilityRepository;
import com.horacio.utils.UploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.*;
import java.util.Date;
import java.util.List;

/**
 * Created by arlex on 2017/12/1.
 */
@Service
public class FacilityService {

    @Autowired
    private FacilityRepository facilityRrepository;

    @Autowired
    private FileProperties fileProperties;



    @Transactional
    public Boolean add(JsonNode data) throws Exception{
        Facility item = new Facility();
        item.setItemName(data.get("itemName").textValue());
        item.setDate(new Date(Long.valueOf(data.get("date").textValue())));
        item.setItemQTY(data.get("itemQTY").intValue());
        item.setUrl(data.get("url").textValue());
        item.setRemainNum(data.get("itemQTY").intValue());
        facilityRrepository.save(item);
        return true;
    }

    @Transactional
    public Boolean edit(JsonNode data) throws Exception{
        Facility item =facilityRrepository.findOne(data.get("id").intValue());
        item.setItemQTY(data.get("itemQTY").intValue());
        item.setUrl(data.get("url").textValue());
        facilityRrepository.save(item);
        return true;
    }


    @Transactional
    public List<Facility> getAll() throws Exception{
        List<Facility> items = facilityRrepository.findAll();
        return  items;
    }

    @Transactional
    public Boolean delete(JsonNode data) throws Exception{
        Facility item = facilityRrepository.findOne(data.get("id").intValue());
        if (item == null){
            return false;
        }else {
            facilityRrepository.delete(item);
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
    public String uploadImage(InputStream stream, String contentType) throws Exception{
        String name = UploadUtil.generatorName();
        String out = fileProperties.getImagePath()+UploadUtil.separatar+name+"."+contentType;
        UploadUtil.upload(stream,out);
        String link = fileProperties.getImageUrl()+name+"."+contentType;
        return link;
    }

}
