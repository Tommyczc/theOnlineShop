package com.theOnlineShop.service.serviceImpl;

import com.theOnlineShop.domain.emailVerificationEntity;
import com.theOnlineShop.mapper.emailListMapper;
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


    @Override
    public boolean emailIsExist(emailVerificationEntity email) {
        List<emailVerificationEntity> emaillist=emailMapper.selectListByEmail(email);
        if(emaillist.size()==0){return true;}
        return false;
    }

    @Override
    public emailVerificationEntity updateEmailVeri(emailVerificationEntity email) {
        List<emailVerificationEntity>emailList=emailMapper.selectListByEmail(email);
        if(emailList.size()!=0){

            Date date = new Date();

            //验证码过期了就刷新,否则返回未过期的验证码
            if( (int) ((date.getTime() - emailList.get(0).getTime().getTime()) / (1000 * 60))>expiredTime){
                emailMapper.updateVerificationCode(email);
                return null;
            }
            else{
                return emailList.get(0);
            }
        }
        else{
            emailMapper.insertVerificationCode(email);
            return null;
        }
    }

    @Override
    public boolean checkEmailVeri(emailVerificationEntity email) {
        List<emailVerificationEntity> emailList=emailMapper.selectListByEmailAndCode(email);

        if(emailList.size()>0){
            if( (int) ((email.getTime().getTime() - emailList.get(0).getTime().getTime()) / (1000 * 60))<=expiredTime){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deleteEmailVeri(emailVerificationEntity email) {
        int i=emailMapper.deleteVerificationCode(email);
        if(i==1){return true;}
        return false;
    }

}
