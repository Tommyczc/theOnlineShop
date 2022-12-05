package com.theOnlineShop.fileStore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;

@Service
public class fileUtils {
    @Autowired
    private osVersion os;

    @PostConstruct
    public void init(){
        File fileFolder=new File(os.getPath());
        //文件存放位置目录不存在就创建
        if (!fileFolder.isDirectory() && !fileFolder.exists()) {
            boolean isCreate=fileFolder.mkdirs();
            Logger logger = LoggerFactory.getLogger(getClass());
            if(!isCreate){
                logger.error("Cannot create upload folder url: "+os.getPath());
            }
            else{logger.info("Have created upload folder url: "+os.getPath());}
        }
    }


    public void storeFile(String path, File file){}

    public void createFolder(String path){}

    public String getCurrentUrl(){
        return os.getPath();
    }
}
