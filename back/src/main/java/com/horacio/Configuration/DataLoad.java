package com.horacio.Configuration;

import com.horacio.Properties.FileProperties;
import com.horacio.utils.EmailUtil;
import com.horacio.utils.UploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * 本类用于系统加载时候的首先执行的项目
 */

@Component
public class DataLoad implements CommandLineRunner {

    @Autowired
    private FileProperties fileProperties;

    @Override
    public void run(String... strings) throws Exception {
        //声明分隔符
        UploadUtil.setSeparater();
        //创建图片文件夹
        String ImageFolder = fileProperties.getImagePath();
        File folder = new File(ImageFolder);
        if(!folder.exists()&&!folder.isDirectory()){
            folder.mkdir();
        }
        EmailUtil.emailConfiguration();
    }
}
