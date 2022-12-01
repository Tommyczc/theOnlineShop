package com.theOnlineShop.service.serviceImpl;

import com.theOnlineShop.domain.permissionEntity;
import com.theOnlineShop.mapper.permissionListMapper;
import com.theOnlineShop.service.permissionListInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class permissionListImpl implements permissionListInter {

    @Autowired
    private permissionListMapper permissionMapper;

    @Override
    public List<permissionEntity> findPermission(permissionEntity permission) {
        return permissionMapper.selectListByRoleName(permission);
    }
}
