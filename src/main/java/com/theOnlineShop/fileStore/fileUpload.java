package com.theOnlineShop.fileStore;

import com.theOnlineShop.domain.userEntity;
import com.theOnlineShop.security.encryption.AesUtils;
import com.theOnlineShop.service.userListInter;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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

    @Transactional(rollbackFor = Exception.class)
    public boolean recoverAvatar(MultipartFile file){
        String userName= SecurityUtils.getSubject().getPrincipals().toString();
        String folderUrl= fileIo.getCurrentUrl()+"/"+userName+"/avatar";
        String fileName=file.getOriginalFilename();
        try {
            //检测之前是否有头像存储，有就删除文件重新写入
            boolean isStroed=fileIo.storeFile(folderUrl,file,true);
            if(!isStroed){return false;}
            //上传头像记录
            userEntity user=new userEntity();
            user.setUserName(AesUtils.encrypt(userName,aesKey));
            user.setHeadSculpture(AesUtils.encrypt(fileName,aesKey));
            boolean isUpload=userList.uploadAvatar(user);
            if(!isUpload){return false;}
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean uploadFile(){
        return false;
    }

    public String getMappingAddress(){
        return mappingAddress+"/**";
    }
}
