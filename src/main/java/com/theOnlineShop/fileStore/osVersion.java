package com.theOnlineShop.fileStore;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class osVersion {
    @Value("${personal.fileStore.windowsStaticFileUrl}")
    private String windowPath;
    @Value("${personal.fileStore.linuxStaticFileUrl}")
    private String linuxPath;
    @Value("${personal.fileStore.appleStaticFileUrl}")
    private String applePath;

    //纯本地文件地址
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

    //映射的本地文件地址(跟上面的函数多了文件前缀，用来表示文件协议)
    public String getFilePath(){
        String os=System.getProperty("os.name");
        if(os!=null&&os.toLowerCase().startsWith("windows")){
            return "file:"+windowPath;
        }
        else if(os!=null&&os.toLowerCase().startsWith("linux")){
            return "file:"+linuxPath;
        }
//        else if(os!=null){
//            System.out.println(os);
//            return null;
//        }
        else{return null;}
    }
}
