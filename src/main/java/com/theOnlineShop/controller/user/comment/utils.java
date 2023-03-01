package com.theOnlineShop.controller.user.comment;

import com.theOnlineShop.domain.emailVerificationEntity;
import com.theOnlineShop.domain.userEntity;
import com.theOnlineShop.fileStore.fileUpload;
import com.theOnlineShop.security.encryption.AesUtils;
import com.theOnlineShop.security.encryption.Md5Utils;
import com.theOnlineShop.security.verification.emailVerification.emailUtils;
import com.theOnlineShop.service.emailListInter;
import com.theOnlineShop.service.userListInter;
import com.theOnlineShop.vo.httpReponseEntity;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;


@Controller
@RequestMapping("/user")
@RequiresRoles(value = {"user","admin","superAdmin"}, logical = Logical.OR)
public class utils {
    @Autowired
    private fileUpload fileUtils;

    @Autowired
    private userListInter userService;

    @Value("${personal.EncryptionKey.aes-key}")
    private String aesKey;
    @Autowired
    private emailUtils emailTools;

    @Autowired
    private emailListInter emailList;

    @RequestMapping(value = ("/avatarUpload"),method = RequestMethod.POST)
    @ResponseBody
    public httpReponseEntity uploadAvatar(@RequestParam(value="avatar",required = true) MultipartFile file){
        boolean isStore=fileUtils.recoverAvatar(file);
        if(!isStore){
            return new httpReponseEntity("warm","头像上传失败");
        }
        return new httpReponseEntity("success","头像上传成功");
    }

    @RequestMapping(value=("/updateInformation"),method = RequestMethod.POST)
    @ResponseBody
    public httpReponseEntity upDateInformation(@RequestParam(value="age",required = true) String age,
                                               @RequestParam(value="address",required = true) String address){
        //System.out.println(SecurityUtils.getSubject().getPrincipals().toString());
        userEntity user=new userEntity();
        user.setUserName(AesUtils.encrypt(SecurityUtils.getSubject().getPrincipals().toString(),aesKey));
        user.setAge(AesUtils.encrypt(age.trim().substring(0,2),aesKey));
        user.setAddress(AesUtils.encrypt(address,aesKey));
        boolean isSuccess=userService.updateUserInformation(user);
        if (isSuccess){return new httpReponseEntity("success","Information Update Success");}
        return new httpReponseEntity("warning","Information Update Fail");
    }

    @RequestMapping(value=("/updatePasswordVerificationCode"),method = RequestMethod.POST)
    @ResponseBody
    public httpReponseEntity getPasswordVerificationCode(){
        userEntity user=new userEntity();
        user.setUserName(AesUtils.encrypt(SecurityUtils.getSubject().getPrincipals().toString(),aesKey));

        user=userService.getUserInformation(user);

        String emailAddress=AesUtils.decrypt(user.getEmail(),aesKey);
        if(emailTools.emailVerification(emailAddress,"更换密码")){
            return new httpReponseEntity("success","Has send the email verification, please login again.");
        }

        return new httpReponseEntity("warning","Could not send verification code.");
    }

    @RequestMapping(value=("/passwordChange"),method = RequestMethod.POST)
    @ResponseBody
    public httpReponseEntity passwordChange(@RequestParam(value="emailVerificationCode",required = true) String code,
                                            @RequestParam(value="pass",required = true) String pass,
                                            @RequestParam(value="checkPass",required = true) String checkPass){

        if(!pass.equals(checkPass)){
            return new httpReponseEntity("warning","Sorry, could not verify the code");
        }
        emailVerificationEntity emailEntity=new emailVerificationEntity();


        userEntity user=new userEntity();
        user.setUserName(AesUtils.encrypt(SecurityUtils.getSubject().getPrincipals().toString(),aesKey));

        user=userService.getUserInformation(user);

        String email=AesUtils.decrypt(user.getEmail(),aesKey);

        emailEntity.setEmail(AesUtils.encrypt(email,aesKey));
        emailEntity.setCode(AesUtils.encrypt(code,aesKey));
        emailEntity.setTime(new Date());
        boolean checkVeri=emailList.checkEmailVeri(emailEntity);
        if(checkVeri){
            //todo 更新密码，通过邮件和用户名
            user.setPassword(Md5Utils.encrypt(pass));
            if(userService.updateUserPassword(user)){
                SecurityUtils.getSubject().logout();
                return new httpReponseEntity("success","User password update success");
            }
            else{
                return new httpReponseEntity("error","User password update fail");
            }
        }
        
        return new httpReponseEntity("warning","Sorry, could not verify the code");
    }
}
