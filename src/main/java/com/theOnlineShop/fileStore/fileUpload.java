package com.theOnlineShop.fileStore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class fileUpload {
    @Autowired
    private fileUtils fileIo;

    public boolean recoverAvatar(File file){
        return false;
    }

    public boolean uploadFile(){
        return false;
    }
}
