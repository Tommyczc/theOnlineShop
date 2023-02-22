package com.theOnlineShop.redis.RedisService;

import com.theOnlineShop.domain.userEntity;

public interface RedisUserListInter {
    public userEntity getLoginInf(String userName);
    public void setLoginInfo(userEntity user);
}
