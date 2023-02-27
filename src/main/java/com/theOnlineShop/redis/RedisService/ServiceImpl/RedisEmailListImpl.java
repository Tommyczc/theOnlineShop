package com.theOnlineShop.redis.RedisService.ServiceImpl;

import com.theOnlineShop.redis.RedisService.RedisEmailListInter;
import com.theOnlineShop.redis.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RedisEmailListImpl implements RedisEmailListInter {
    @Autowired
    private RedisUtil redisUtil;

    @Value("${personal.mail.expiredTime}")
    private int expiredTime;
    @Override
    public boolean setVerificationCode(String email, String code) {
        try{
        redisUtil.set(email,code,expiredTime* 60L);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public Object getVerificationCode(String email) {
        return redisUtil.get(email);
    }

    @Override
    public boolean deleteVerificationCode(String email) {
        return false;
    }
}
