package com.theOnlineShop.security.emailVerification;


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
    /**
     * 邮件发件人
     */
    @Value("${mail.fromMail.fromAddress}")
    private String fromAddress;
    @Autowired
    TemplateEngine templateEngine;
    @Autowired
    private VerificationCodeGeneration verificationCodeService;

    public boolean sendEmailVerificationCode(String toAddress) {
        //调用 VerificationCodeService 生产验证码
        String verifyCode = verificationCodeService.generateVerificationCode();
        //创建邮件正文
        Context context = new Context();
        context.setVariable("verifyCode", Arrays.asList(verifyCode.split("")));
        //将模块引擎内容解析成html字符串
        String emailContent = templateEngine.process("emailVerificationPage", context);
        MimeMessage message=mailSender.createMimeMessage();
        try {
            //true表示需要创建一个multipart message
            MimeMessageHelper helper=new MimeMessageHelper(message,true);
            helper.setFrom(fromAddress);
            helper.setTo(toAddress);
            helper.setSubject("注册验证码");
            helper.setText(emailContent,true);
            mailSender.send(message);
            return true;
        }catch (MessagingException e) {
            return false;
        }
    }
}
