package com.horacio.utils;

import com.horacio.Enum.ResultEnum;
import com.horacio.Exception.LabsException;
import com.horacio.Properties.FileProperties;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineUtils;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.codec.binary.Base64;

@Component
public class EmailUtil {

    @Autowired
    private JavaMailSender email;

    @Autowired
    private VelocityEngine velocityEngine;

    @Autowired
    private FileProperties fileProperties;

    //发送邮件
    public void send(String destination,String name,String itemName,String borrowDate,String image,int number,int month) throws Exception{
        //检查对应邮件地址是否合法
        try{
            String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(destination);
            matcher.matches();
        }catch (Exception e){
            throw new LabsException(ResultEnum.EMAIL_FORMAT_ERROR.getCode(),ResultEnum.EMAIL_FORMAT_ERROR.getMsg());
        }
        // 声明Map对象，并填入用来填充模板文件的键值对
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("name", name);
        model.put("borrowDate", borrowDate);
        model.put("itemName", itemName);
        model.put("number", number);
        model.put("month", month);
        String base64 = "";
        if(image!=null){
            String type = image.substring(image.indexOf(".")+1);
            String uri = fileProperties.getImagePath()+UploadUtil.separatar+image;
            InputStream in = null;
            byte[] data = null;
            // 读取文件字节数组
            try {
                in = new FileInputStream(uri);
                data = new byte[in.available()];
                in.read(data);
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                in.close();
            }
            // 返回Base64编码过的字节数组字符串
            base64 = "data:image/"+type+";base64,"+new String(Base64.encodeBase64(data)).replace("/\n/g","");
        }
        model.put("src", base64);
        //Spring提供的VelocityEngineUtils将模板进行数据填充，并转换成普通的String对象
        //文件地址相对应src目录
        String emailText = VelocityEngineUtils.mergeTemplateIntoString(
                velocityEngine, "mail.vm","UTF-8", model);
        MimeMessage message = email.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom("tjsse510@163.com");
        helper.setTo(destination);
        helper.setSubject("同济大学软件学院510实验室");
        helper.setText(emailText,true);
        email.send(message);
    }
}
