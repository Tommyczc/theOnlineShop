package com.theOnlineShop.fileStore;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class fileUpload {
    @Autowired
    private fileUtils fileIo;

    public boolean recoverAvatar(File file){
        String userName=SecurityUtils.getSubject().getPrincipals().toString();
        return false;
    }

    public boolean uploadFile(){
        return false;
    }
}
