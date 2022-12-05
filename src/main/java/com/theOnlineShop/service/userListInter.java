package com.theOnlineShop.service;

import com.theOnlineShop.domain.userEntity;

import java.util.List;


public interface userListInter {
    public boolean login(userEntity user);
    public boolean emailDuplicateCheck(userEntity user);
    public boolean userNameDuplicateCheck(userEntity user);
    public boolean register(userEntity user);
    public List<userEntity> getUserInformation(userEntity user);
    public boolean uploadAvatar(userEntity user);
}
