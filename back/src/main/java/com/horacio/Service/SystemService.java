package com.horacio.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.horacio.Enum.ResultEnum;
import com.horacio.Exception.LabsException;
import com.horacio.Model.Admin;
import com.horacio.Model.Facility;
import com.horacio.Properties.FileProperties;
import com.horacio.Repository.AdminRepository;
import com.horacio.Repository.FacilityRepository;
import com.horacio.utils.UploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by arlex on 2017/12/1.
 */
@Service
public class SystemService {

    @Autowired
    private FacilityRepository facilityRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private FileProperties fileProperties;


    public void slim() throws Exception{
        cleanUserImages(fileProperties.getUserPath());
        cleanFacilityImages(fileProperties.getFacilityPath());
    }

    /**
     * 清理冗余文件
     * @throws Exception
     */

    public void cleanUserImages(String imagePath) throws Exception{
        File folder = new File(imagePath);
        if(!folder.exists()){
            throw new LabsException(ResultEnum.FILE_FOLDER_NOT_FOUND.getCode(),ResultEnum.FILE_FOLDER_NOT_FOUND.getMsg());
        }
        File images[] = folder.listFiles();
        Admin admin = null;
        for(int i=0;i<images.length;i++){
            File image = images[i];
            String name = image.getName();
            admin= adminRepository.findOneByUrl(name);
            if(admin == null){
                image.delete();
            }
        }
    }

    public void cleanFacilityImages(String imagePath) throws Exception{
        File folder = new File(imagePath);
        if(!folder.exists()){
            throw new LabsException(ResultEnum.FILE_FOLDER_NOT_FOUND.getCode(),ResultEnum.FILE_FOLDER_NOT_FOUND.getMsg());
        }
        File images[] = folder.listFiles();
        Facility facility = null;
        for(int i=0;i<images.length;i++){
            File image = images[i];
            String name = image.getName();
            facility= facilityRepository.findOneByUrl(name);
            if(facility == null){
                image.delete();
            }
        }
    }

}
