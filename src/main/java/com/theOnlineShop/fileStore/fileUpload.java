package com.theOnlineShop.fileStore;

import com.theOnlineShop.domain.userEntity;
import com.theOnlineShop.security.encryption.AesUtils;
import com.theOnlineShop.service.userListInter;
import org.apache.shiro.SecurityUtils;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class fileUpload {
    @Autowired
    private fileUtils fileIo;

    @Autowired
    private userListInter userList;

    @Value("${personal.EncryptionKey.aes-key}")
    private String aesKey;

    @Value("${personal.fileStore.mappingAddress}")
    private String mappingAddress;

    @Transactional(rollbackFor = IOException.class)
    public boolean recoverAvatar(MultipartFile file){
        String userName=SecurityUtils.getSubject().getPrincipals().toString();
        String folderUrl= fileIo.getCurrentUrl()+"/"+userName+"/avatar";
        String fileName=file.getOriginalFilename();
        try {
            //检测之前是否有头像存储，有就删除文件重新写入
            File detectPath=new File(folderUrl);
            if(detectPath.exists() && detectPath.isDirectory()){
                FileUtils.deleteDirectory(detectPath);
            }

            detectPath.mkdirs();
            file.transferTo(new File(folderUrl,file.getOriginalFilename()));
            //上传头像记录
            userEntity user=new userEntity();
            user.setUserName(AesUtils.encrypt(userName,aesKey));
            user.setHeadSculpture(AesUtils.encrypt(fileName,aesKey));
            boolean isUpload=userList.uploadAvatar(user);
            if(!isUpload){return false;}
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public boolean uploadFile(){
        return false;
    }

    public String getMappingAddress(){
        return mappingAddress;
    }
}
