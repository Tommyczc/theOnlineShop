package com.theOnlineShop.security.verification.emailVerification;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Arrays;

@Service
public class EmailService  {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${personal.mail.fromMail.fromAddress}")
    private String fromAddress;

    @Autowired
    TemplateEngine templateEngine;

    @Value("${personal.mail.expiredTime}")
    private int expiredTime;

    public boolean sendEmailVerificationCode(String toAddress, String verifyCode, String behavior) {

        //创建邮件正文
        Context context = new Context();
        context.setVariable("verifyCode", Arrays.asList(verifyCode.split("")));
        context.setVariable("behavior",behavior);
        context.setVariable("expiredTime",expiredTime);
        //将模块引擎内容解析成html字符串
        String emailContent = templateEngine.process("verificationPage/emailVerificationPage", context);
        MimeMessage message=mailSender.createMimeMessage();
        try {
            //true表示需要创建一个multipart message
            MimeMessageHelper helper=new MimeMessageHelper(message,true);
            helper.setFrom(fromAddress);
            helper.setTo(toAddress);
            helper.setSubject(behavior+"验证码: "+verifyCode);
            helper.setText(emailContent,true);
            mailSender.send(message);
            return true;
        }catch (MessagingException e) {
            return false;
        }
    }
}
