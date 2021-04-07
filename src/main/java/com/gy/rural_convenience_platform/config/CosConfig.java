package com.gy.rural_convenience_platform.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "cos")
public class CosConfig {

    /** 腾讯云服务器AppId.*/
    private String appId;

    /** 腾讯云SecretId.*/
    private String secretId;

    /** 腾讯云SecretKey.*/
    private String secretKey;

    /** 存储桶名称.*/
    private String bucketName;

    /** 访问静态资源路径前缀.*/
    private String domainName;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getSecretId() {
        return secretId;
    }

    public void setSecretId(String secretId) {
        this.secretId = secretId;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    @Override
    public String toString() {
        return "CosConfig{" +
                "appId='" + appId + '\'' +
                ", secretId='" + secretId + '\'' +
                ", secretKey='" + secretKey + '\'' +
                ", bucketName='" + bucketName + '\'' +
                ", domainName='" + domainName + '\'' +
                '}';
    }
}
