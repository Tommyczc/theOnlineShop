package com.theOnlineShop.redis.RedisService;

public interface RedisEmailListInter {
    public boolean setVerificationCode(String email,String code);
    public Object getVerificationCode(String email);
    public boolean deleteVerificationCode(String email);
}
