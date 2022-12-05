package com.theOnlineShop.webConfig;

import com.theOnlineShop.fileStore.osVersion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private osVersion os;

    @Value("${personal.fileStore.mappingAddress}")
    private String mappingAddress;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //浏览器访问路径中带有/document/时，将被映射到所设置的地址中
        registry.addResourceHandler(mappingAddress).addResourceLocations("file:/"+os.getPath());
    }
}
