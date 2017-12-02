package com.horacio.Controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.horacio.Enum.ResultEnum;
import com.horacio.Exception.LabsException;
import com.horacio.Service.FacilityService;
import com.horacio.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.InputStream;
import java.util.List;

/**
 * Created by arlex on 2017/12/1.
 */
@RestController
@RequestMapping(value = "/facility")
public class FacilityController {

    @Autowired
    FacilityService facilityService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Object add(@RequestBody JsonNode body) throws Exception{
        facilityService.add(body);
        return ResultUtil.success();
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public Object edit(@RequestBody JsonNode body) throws Exception{
        facilityService.edit(body);
        return ResultUtil.success();
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public Object getall() throws Exception{
        ArrayNode facilities = facilityService.getAll();
        return ResultUtil.success(facilities);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Object delete(@RequestBody JsonNode body)throws Exception{
        facilityService.delete(body);
        return ResultUtil.success();

    }

    @PostMapping(value = "/upload")
    public Object upload(@RequestParam("file") MultipartFile picture) throws Exception{
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
        InputStream file = picture.getInputStream();
        String contentType = picture.getContentType().split("/")[1];
        String filePath = facilityService.uploadImage(file,contentType);
        return ResultUtil.success(filePath);
    }

    /**
     * 删除冗余的图片
     * @throws Exception
     */
    @GetMapping(value = "/slim")
    public Object showFrame() throws Exception{
        facilityService.clean();
        return ResultUtil.success();
    }

}
