package com.theOnlineShop.controller.user.comment;

import com.theOnlineShop.domain.userEntity;
import com.theOnlineShop.fileStore.fileUpload;
import com.theOnlineShop.security.encryption.AesUtils;
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

import java.io.IOException;


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
        return new httpReponseEntity("warn","Information Update Fail");
    }
}
