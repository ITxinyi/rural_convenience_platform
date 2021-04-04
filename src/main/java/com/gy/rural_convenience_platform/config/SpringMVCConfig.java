package com.gy.rural_convenience_platform.config;

import com.gy.rural_convenience_platform.filter.FilterConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SuppressWarnings("deprecation")
@SpringBootConfiguration
public class SpringMVCConfig implements WebMvcConfigurer {

    @Autowired
    private FilterConfig filterConfig;

    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(filterConfig).addPathPatterns("/**");
    }
}
