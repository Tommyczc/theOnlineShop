package com.theOnlineShop.service;

import com.theOnlineShop.domain.userEntity;

import java.util.List;


public interface userListInter {
    public boolean login(userEntity user);
    public boolean emailDuplicateCheck(userEntity user);
    public boolean userNameDuplicateCheck(userEntity user);
    public boolean register(userEntity user);
    public userEntity getUserInformation(userEntity user);
    public boolean uploadAvatar(userEntity user);
    public boolean updateUserInformation(userEntity user);
    public boolean updateUserPassword(userEntity user);
}
