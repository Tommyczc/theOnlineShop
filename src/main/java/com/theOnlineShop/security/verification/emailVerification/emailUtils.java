package com.theOnlineShop.security.verification.emailVerification;

import com.theOnlineShop.domain.emailVerificationEntity;
import com.theOnlineShop.security.encryption.AesUtils;
import com.theOnlineShop.service.emailListInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.util.Date;


@Service
public class emailUtils {
    @Autowired
    private EmailService emailService;
    @Autowired
    private VerificationCodeGeneration codeGeneration;
    @Autowired
    private emailListInter emailInter;
    @Value("${personal.EncryptionKey.aes-key}")
    private String aesKey;


    public void emailVerification(String email,String behavior){
        String code=codeGeneration.generateVerificationCode();
        emailVerificationEntity emailEntity=new emailVerificationEntity();
        emailEntity.setEmail(AesUtils.encrypt(email,aesKey));
        emailEntity.setCode(AesUtils.encrypt(code, aesKey));
        emailEntity.setTime(new Date());

        emailVerificationEntity emailExist;
        emailExist=emailInter.updateEmailVeri(emailEntity);

        if(emailExist!=null){
            String decryCode= AesUtils.decrypt(emailExist.getCode(),aesKey);
            code=decryCode;
        }
        emailService.sendEmailVerificationCode(email,code,behavior);
    }
}
