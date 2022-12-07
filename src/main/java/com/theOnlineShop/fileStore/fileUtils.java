package com.theOnlineShop.fileStore;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;

@Service
public class fileUtils {
    @Autowired
    private osVersion os;

    @PostConstruct
    public void init(){
        File fileFolder=new File(os.getPath());
        Logger logger = LoggerFactory.getLogger(getClass());
        //文件存放位置目录不存在就创建
        if (!fileFolder.isDirectory() && !fileFolder.exists()) {
            boolean isCreate=fileFolder.mkdirs();
            if(!isCreate){
                logger.error("Cannot create upload folder url: "+os.getPath());
            }
        }
        else{logger.info("Have created upload folder url: "+os.getPath());}
    }


    public boolean storeFile(String path, MultipartFile file, boolean isUnique){
        try {
            File detectPath = new File(path);
            if(isUnique) {
                //检测之前是否有头像存储，有就删除文件重新写入
                if (detectPath.exists() && detectPath.isDirectory()) {
                    FileUtils.deleteDirectory(detectPath);
                }

                detectPath.mkdirs();
                file.transferTo(new File(path, file.getOriginalFilename()));
            }
            else{
                if (!detectPath.exists() && !detectPath.isDirectory()) {
                    detectPath.mkdirs();
                }
                //由于文件夹允许多个文件存在，所以文件名需要加个时间轴前缀
                long timeMillis = System.currentTimeMillis();
                String fileUrl=String.valueOf(timeMillis)+file.getOriginalFilename();
                file.transferTo(new File(path, fileUrl));
            }
        }catch(IOException e){
            return false;
        }
        return true;
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
