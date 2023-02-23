package com.theOnlineShop.redis.RedisService.ServiceImpl;

import com.theOnlineShop.domain.userEntity;
import com.theOnlineShop.redis.RedisService.RedisUserListInter;
import com.theOnlineShop.redis.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RedisUserListImpl implements RedisUserListInter {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public userEntity getLoginInf(String userName) {
        userEntity user=(userEntity) redisUtil.hGet("userList",userName);
        return user;
    }

    @Override
    public void setLoginInfo(userEntity user) {
        redisUtil.hSet("userList", user.getUserName(),user);
    }

    @Override
    public void deleteUser(userEntity user) {
        redisUtil.delete("userList",user.getUserName());
    }


}
