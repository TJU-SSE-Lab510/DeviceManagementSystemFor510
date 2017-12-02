package com.horacio.utils;

import com.horacio.Enum.ResultEnum;
import com.horacio.Exception.LabsException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailUtil {

    private static JavaMailSenderImpl email;

    public static void emailConfiguration() {
        // TODO Auto-generated constructor stub
        email = new JavaMailSenderImpl();
        email.setHost("smtp.163.com");
        email.setUsername("tjsse510@163.com");
        email.setPassword("510015abcd");//授权码
        email.setPort(465);
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.ssl.enable", true);
        properties.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.timeout", 25000);
        email.setJavaMailProperties(properties);
    }

    //发送邮件
    public static void send(String destination){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("tjsse510@163.com");
        try{
            String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(destination);
            matcher.matches();
        }catch (Exception e){
            throw new LabsException(ResultEnum.EMAIL_FORMAT_ERROR.getCode(),ResultEnum.EMAIL_FORMAT_ERROR.getMsg());
        }
        message.setTo(destination);
        message.setSubject("同济大学软件学院510实验室");
        message.setText("设备管理处");
        email.send(message);
    }
}
