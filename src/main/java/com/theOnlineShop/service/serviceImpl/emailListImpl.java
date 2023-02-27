package com.theOnlineShop.service.serviceImpl;

import com.theOnlineShop.domain.emailVerificationEntity;
import com.theOnlineShop.mapper.emailListMapper;
import com.theOnlineShop.redis.RedisService.RedisEmailListInter;
import com.theOnlineShop.redis.RedisService.RedisUserListInter;
import com.theOnlineShop.service.emailListInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class emailListImpl implements emailListInter {
    @Autowired
    private emailListMapper emailMapper;
    @Value("${personal.mail.expiredTime}")
    private int expiredTime;
    @Autowired
    RedisEmailListInter redisEmailMapper;

    @Override
    public boolean emailIsExist(emailVerificationEntity email) {
        List<emailVerificationEntity> emaillist=emailMapper.selectListByEmail(email);
        if(emaillist.size()==0){return true;}
        return false;
    }

    /**
     * 更新邮箱验证码，redis插入，验证邮箱存在就覆盖验证码，没有就插入
     * @param email
     * @return
     */
    @Override
    public emailVerificationEntity updateEmailVeri(emailVerificationEntity email) {
        redisEmailMapper.setVerificationCode(email.getEmail(), email.getCode());
        return email;

    }

    @Override
    public boolean checkEmailVeri(emailVerificationEntity email) {
//        List<emailVerificationEntity> emailList=emailMapper.selectListByEmailAndCode(email);
//
//        if(emailList.size()>0){
//            if( (int) ((email.getTime().getTime() - emailList.get(0).getTime().getTime()) / (1000 * 60))<=expiredTime){
//                return true;
//            }
//        }
        // todo check code in redis instead of sql
        String emailAddress=email.getEmail();
        String code=email.getCode();
        String theCode=(String) redisEmailMapper.getVerificationCode(emailAddress);
        return code.equals(theCode);
    }

    @Override
    public boolean deleteEmailVeri(emailVerificationEntity email) {
//        int i=emailMapper.deleteVerificationCode(email);
//        if(i==1){return true;}
        return false;
    }

}
