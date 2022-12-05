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


    public void storeFile(String path, File file){

    }

    public void createFolder(String path){

    }

    public String getCurrentUrl(){
        return os.getPath();
    }

    public boolean checkFileSize(Long len, int size, String unit) {
//        long len = file.length();
        double fileSize = 0;
        if ("B".equals(unit.toUpperCase())) {
            fileSize = (double) len;
        } else if ("K".equals(unit.toUpperCase())) {
            fileSize = (double) len / 1024;
        } else if ("M".equals(unit.toUpperCase())) {
            fileSize = (double) len / 1048576;
        } else if ("G".equals(unit.toUpperCase())) {
            fileSize = (double) len / 1073741824;
        }
        if (fileSize > size) {
            return false;
        }
        return true;
    }
}
