package com.gy.rural_convenience_platform.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

/**
 * 上传文件工具类
 *
 * @author Administrator
 */
public class UploadUtil {
    /**
     * 只需要关注 入参  出参
     * 传入一个文件，返回文件名称
     *
     * @param file
     * @return
     */
    public static String uploadFile(MultipartFile file) {
        String origName = file.getOriginalFilename();
        String newFileName = null;
        if (origName != null && origName.length() > 0) {
            //获取后缀名   123.jpg   test.ttt.png
            String extendsName = origName.substring(origName.lastIndexOf("."));
            //定义上传文件保存路径
            String filePath = "E:\\Users\\dell\\workspace2020\\青软实训\\upload\\";
            File dir = new File(filePath);
            if (!dir.exists()) {
                dir.mkdir();
            }
            //定义新的文件名称    路径 + 名称
            newFileName = UUID.randomUUID().toString() + extendsName;
            //创建文件
            File newFile = new File(filePath + newFileName);
            try {
                //上传文件
                file.transferTo(newFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return newFileName;
    }
}
