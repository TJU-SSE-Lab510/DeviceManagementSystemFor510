package com.horacio.utils;

import com.horacio.Enum.ResultEnum;
import com.horacio.Exception.LabsException;

import java.io.*;
import java.util.Random;

public class UploadUtil {


    //获得当前服务器的文件分隔符
    public static String separatar;

    public static void setSeparater(){
        separatar = File.separator;
    }
    /**
     * 生成随机文件名称
     * @return
     */
    public static String generatorName(){
        Random random= new Random();
        int choice = random.nextInt(2)%2 == 0 ? 65:97;
        char prefix = (char)(choice+random.nextInt(26));
        String date = Long.toString(System.currentTimeMillis());
        String name = prefix + date;
        return name;
    }

    /**
     * 保存文件
     * @param stream
     * @param path
     * @throws Exception
     */
    public static void upload(InputStream stream, String path) throws Exception{
        try{
//          创建文件
            File file = new File(path);
//          创建输出文件流
            FileOutputStream outStream = new FileOutputStream(file);
//          创建buffer
            BufferedOutputStream outBuffer = new BufferedOutputStream(outStream);
//          将获得字节流写入文件
            byte[] data = new byte[1024];
            int len = 0;
            while ((len = stream.read(data)) != -1) {
                outBuffer.write(data,0,len);
            }
            outBuffer.flush();
            outBuffer.close();
        }catch (FileNotFoundException e){
            throw new LabsException(ResultEnum.FILE_NOT_FOUND.getCode(),ResultEnum.FILE_NOT_FOUND.getMsg());
        }catch (IOException e){
            throw new LabsException(ResultEnum.FILE_UPLOAD_FAILED.getCode(),ResultEnum.FILE_UPLOAD_FAILED.getMsg());
        }
    }

    /**
     * 删除文件
     * @param path
     * @throws Exception
     */
    public static void delete(String path) throws Exception{
        File file = new File(path);
        if(file.exists()){
            file.delete();
        }
    }
}
