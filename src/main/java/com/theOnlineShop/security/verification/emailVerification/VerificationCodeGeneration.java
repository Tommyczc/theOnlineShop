package com.theOnlineShop.security.verification.emailVerification;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class VerificationCodeGeneration{
    /**
     * 生产的验证码位数
     */

    private final int generateVerificationCodeLength=5;
    private final String[] metaCode={"0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O",
            "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};


    public String generateVerificationCode() {
        Random random = new Random();
        StringBuilder verificationCode = new StringBuilder();
        while (verificationCode.length()<generateVerificationCodeLength){
            int i = random.nextInt(metaCode.length);
            verificationCode.append(metaCode[i]);
        }
        return verificationCode.toString();
    }
}

