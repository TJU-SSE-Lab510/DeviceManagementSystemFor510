package com.horacio.Configuration;

import com.horacio.Properties.EmailProperties;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.ui.velocity.VelocityEngineFactoryBean;

import javax.persistence.EntityManagerFactory;
import java.util.Properties;

@Configuration
public class DataConfig {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    private EmailProperties emailProperties;

    //Criteria动态查询配置
    @Bean
    public SessionFactory getSessionFactory() {
        if (entityManagerFactory.unwrap(SessionFactory.class) == null) {
            throw new NullPointerException("Not a hibernate factory exception");
        }
        return entityManagerFactory.unwrap(SessionFactory.class);
    }

    //配置发送邮件信息
    @Bean
    public JavaMailSender mailSender(){
        JavaMailSenderImpl email = new JavaMailSenderImpl();
        email.setHost(emailProperties.getHost());
        email.setUsername(emailProperties.getUsername());
        email.setPassword(emailProperties.getPassword());//授权码
        email.setPort(465);
        email.setDefaultEncoding("UTF-8");
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.ssl.enable", true);
        properties.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.timeout", 25000);
        email.setJavaMailProperties(properties);
        return email;
    }

    //发送Velocity模板邮件配置
    @Bean
    public VelocityEngineFactoryBean velocityEngine(){
        VelocityEngineFactoryBean velocityEngine = new VelocityEngineFactoryBean();

        Properties properties = new Properties();
        properties.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH,"./src/main/resources/");
        properties.setProperty("resource.loader", "classpath");
        properties.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        properties.setProperty(Velocity.ENCODING_DEFAULT,"UTF-8");
        properties.setProperty(Velocity.INPUT_ENCODING,"UTF-8");
        properties.setProperty(Velocity.OUTPUT_ENCODING,"UTF-8");

        velocityEngine.setVelocityProperties(properties);

        return velocityEngine;
    }
}
