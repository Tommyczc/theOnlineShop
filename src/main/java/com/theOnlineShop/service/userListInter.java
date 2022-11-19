package com.theOnlineShop.service;

import com.theOnlineShop.domain.userEntity;


public interface userListInter {
    public boolean login(userEntity user);
    public boolean emailDuplicateCheck(userEntity user);
    public boolean userNameDuplicateCheck(userEntity user);
}
