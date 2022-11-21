package com.theOnlineShop.service;

import com.theOnlineShop.domain.permissionEntity;

import java.util.List;

public interface permissionListInter {
    public List<permissionEntity> findPermission(permissionEntity permission);
}
