package com.haptiq.secureBlogTest.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class StaticResourceConfig implements WebMvcConfigurer {

    @Value("${file.upload-dir}")
    private String uploadDir;


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String normalizedPath = uploadDir.replace("\\", "/");  // Replace backslashes with forward slashes
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + normalizedPath + "/");
    }
}