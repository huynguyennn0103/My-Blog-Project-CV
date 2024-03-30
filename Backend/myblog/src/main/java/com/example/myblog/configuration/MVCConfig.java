package com.example.myblog.configuration;

import com.example.myblog.interceptor.InterceptorAppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class MVCConfig implements WebMvcConfigurer {
    @Autowired
    private InterceptorAppConfig interceptorAppConfig;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptorAppConfig);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        Path avaUploadDir = Paths.get("./public");
        String avaUploadPath = avaUploadDir.toFile().getAbsolutePath();
        registry.addResourceHandler("/public/**").addResourceLocations("file:/" + avaUploadPath + "/");
    }
}
