package com.gy.rural_convenience_platform.utils;

import com.gy.rural_convenience_platform.config.CosConfig;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Date;
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

    /**
     * 上传文件到腾讯云对象存储
     * @param files
     * @return 返回对象访问地址
     */
    public static String uploadToOos(MultipartFile files,String folder,CosConfig cosConfig) throws IOException, URISyntaxException {

        // 1 初始化用户身份信息（secretId, secretKey）。
        String secretId = cosConfig.getSecretId();
        String secretKey = cosConfig.getSecretKey();
        String bucketName = cosConfig.getBucketName();
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        // 2 设置 bucket 的地域, COS 地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
        // clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分。
        Region region = new Region("ap-shanghai");
        ClientConfig clientConfig = new ClientConfig(region);
        // 这里建议设置使用 https 协议
        clientConfig.setHttpProtocol(HttpProtocol.https);
        // 3 生成 cos 客户端。
        COSClient cosClient = new COSClient(cred, clientConfig);

        // 指定要上传的文件
        String localFilePath = files.getOriginalFilename();
        //获取后缀名   123.jpg   test.ttt.png
        String extendsName = localFilePath.substring(localFilePath.lastIndexOf("."));
        //定义新的文件名称    路径 + 名称
        String newFileName = UUID.randomUUID().toString() + extendsName;

        File localFile = new File(localFilePath);

//
        InputStream inputStream = files.getInputStream();
        inputStreamToFile(inputStream,localFile);
//

        // 指定文件将要存放的存储桶
        // 指定文件上传到 COS 上的路径，即对象键。例如对象键为folder/picture.jpg，则表示将文件 picture.jpg 上传到 folder 路径下
        String key = folder + "/" + newFileName;
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, localFile);
        cosClient.putObject(putObjectRequest);
        cosClient.shutdown();
        /*过期时间*/
        Date expiration = new Date(System.currentTimeMillis() + 5 * 60 * 10000);
        URL url = cosClient.generatePresignedUrl(bucketName, key, expiration);

        File del = new File(localFile.toURI());
        del.delete();

        if (url == null) {
            return null;
        }
        return cosConfig.getDomainName() + "/" + key;
    }

    /**
     * 接口描述：MultipartFile 转换为 File
     */
    public static void inputStreamToFile(InputStream ins, File file) {
        OutputStream os = null;
        try {
            os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != os) {
                    os.close();
                }
                if (null != ins) {
                    ins.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
