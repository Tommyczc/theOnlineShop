package com.theOnlineShop.service.serviceImpl;

import com.theOnlineShop.domain.userEntity;
import com.theOnlineShop.mapper.userListMapper;
import com.theOnlineShop.service.userListInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class userListImpl implements userListInter {
    @Autowired
    userListMapper userMapper;


    @Override
    public boolean login(userEntity user) {
        List<userEntity> userList=userMapper.loginByPasswordAndUserName(user);
        if(userList.size()==1){
            return true;
        }
        return false;
    }

    /**
     * 检测数据库该邮箱是否用于其他账号的邮箱
     */
    @Override
    public boolean emailDuplicateCheck(userEntity user) {
        List<userEntity> userList=userMapper.selectListByUserNameAndEmail(user);
        if(userList.size()==0){
            return true;
        }
        return false;
    }
}
