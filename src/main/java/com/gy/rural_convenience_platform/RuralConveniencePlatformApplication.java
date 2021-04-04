package com.gy.rural_convenience_platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@MapperScan(basePackages = "com.gy.rural_convenience_platform.mapper")
@SpringBootApplication
public class RuralConveniencePlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(RuralConveniencePlatformApplication.class, args);
    }

}
