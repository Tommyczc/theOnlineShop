package com.theOnlineShop.fileStore;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.awt.*;

@Service
public class osVersion {
    @Value("${personal.fileStore.windowsStaticFileUrl}")
    private String windowPath;
    @Value("${personal.fileStore.linuxStaticFileUrl}")
    private String linuxPath;

    public String getPath(){
        String os=System.getProperty("os.name");
        if(os!=null&&os.toLowerCase().startsWith("windows")){
            return windowPath;
        }
        else if(os!=null&&os.toLowerCase().startsWith("linux")){
            return linuxPath;
        }
        else{return null;}
    }
}
