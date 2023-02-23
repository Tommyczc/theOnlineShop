package com.theOnlineShop.controller.user;

import com.theOnlineShop.domain.userEntity;
import com.theOnlineShop.security.encryption.AesUtils;
import com.theOnlineShop.service.userListInter;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class baseInformation {

    @Autowired
    private userListInter userService;

    @Value("${personal.EncryptionKey.aes-key}")
    private String aesKey;

    public Model getPersonalInformationLogic(Model model){
        //查询
        userEntity user=new userEntity();
        user.setUserName(AesUtils.encrypt(SecurityUtils.getSubject().getPrincipals().toString(),aesKey));
        user=userService.getUserInformation(user);

        //解密
        userEntity deUser=new userEntity();
        deUser.setUserName(SecurityUtils.getSubject().getPrincipals().toString());
        if(user.getHeadSculpture()!=null && !user.getHeadSculpture().isEmpty()){
            deUser.setHeadSculpture("/staticDocument"+"/"+SecurityUtils.getSubject().getPrincipals().toString()+"/"+"avatar/"+AesUtils.decrypt(user.getHeadSculpture(),aesKey));
        }
        else{
            //加入默认头像
            deUser.setHeadSculpture("/image/avatar/user.png");
        }

        deUser.setEmail(AesUtils.decrypt(user.getEmail(),aesKey));

        if(user.getAge()==null || user.getAge().isEmpty()){
            deUser.setAge("no information");
        }
        else{
            deUser.setAge(AesUtils.decrypt(user.getAge(),aesKey));
        }

        if(user.getAddress()==null || user.getAddress().isEmpty()){
            deUser.setAddress("no information");
        }
        else{
            deUser.setAddress(AesUtils.decrypt(user.getAddress(),aesKey));
        }

        deUser.setRegisterTime(user.getRegisterTime());
        //转化时间格式
        SimpleDateFormat formate=new SimpleDateFormat("yyyy-MM-dd");
        String registerDate=formate.format(user.getRegisterTime());

        model.addAttribute("user",deUser);
        model.addAttribute("registerDate",registerDate);
        return model;
    }
}
