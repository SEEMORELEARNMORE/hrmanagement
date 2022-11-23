package com.hrm.hrmanagement.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        //配置资源映射：设置虚拟路径，访问绝对路径下资源：访问 http://localhost:9090/api/file/xxx.txt访问d:///uploadFiles/下的资源
        registry.addResourceHandler("/api/file/**") //虚拟路径
                .addResourceLocations("file:" + "D:///szzb-welfarework/import/img/zzysj/"); //绝对路径
    }

}

