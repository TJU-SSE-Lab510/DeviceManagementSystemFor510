package com.horacio.Controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.horacio.Enum.ResultEnum;
import com.horacio.Exception.LabsException;
import com.horacio.Service.FacilityService;
import com.horacio.Service.SystemService;
import com.horacio.utils.AuthCheckUtil;
import com.horacio.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.InputStream;

/**
 * Created by arlex on 2017/12/1.
 */
@RestController
@RequestMapping(value = "/system")
public class SystemController {

    @Autowired
    SystemService systemService;

    /**
     * 删除冗余的图片
     * @throws Exception
     */
    @GetMapping(value = "/slim")
    public Object slim() throws Exception{
        systemService.slim();
        return ResultUtil.success();
    }

}
