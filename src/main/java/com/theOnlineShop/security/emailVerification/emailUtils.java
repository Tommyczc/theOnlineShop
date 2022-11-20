package com.theOnlineShop.security.emailVerification;

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
        emailEntity.setEmail(email);
        emailEntity.setCode(AesUtils.encrypt(code,AesUtils.getKeyByPass(aesKey)));
        emailEntity.setTime(new Date());

        emailVerificationEntity emailExist;
        System.out.println(emailEntity.getCode());
        emailExist=emailInter.updateEmailVeri(emailEntity);

        if(emailExist!=null){
            System.out.println("not expired: "+emailExist.getCode());
            String decryCode=AesUtils.decrypt(emailExist.getCode(),AesUtils.getKeyByPass(aesKey));
        }
    }
}
