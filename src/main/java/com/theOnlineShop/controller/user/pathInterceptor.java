package com.theOnlineShop.controller.user;

import com.theOnlineShop.domain.userEntity;
import com.theOnlineShop.security.encryption.AesUtils;
import com.theOnlineShop.service.userListInter;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/user")
@RequiresRoles(value = {"user","admin","superAdmin"}, logical = Logical.OR)
public class pathInterceptor {

    @Autowired
    private userListInter userService;

    @Value("${personal.EncryptionKey.aes-key}")
    private String aesKey;

    @RequestMapping("/personalPage")
    public String personalInformation(Model model){
        //查询
        userEntity user=new userEntity();
        user.setUserName(AesUtils.encrypt(SecurityUtils.getSubject().getPrincipals().toString(),aesKey));
        List<userEntity> userList=userService.getUserInformation(user);
        user=userList.get(0);

        //解密
        userEntity deUser=new userEntity();
        deUser.setUserName(SecurityUtils.getSubject().getPrincipals().toString());
        if(userList.get(0).getHeadSculpture()!=null){
            deUser.setHeadSculpture(AesUtils.decrypt(userList.get(0).getHeadSculpture(),aesKey));
        }
        else{
            //加入默认头像
            deUser.setHeadSculpture("/image/avatar/user.png");
        }

        deUser.setEmail(AesUtils.decrypt(user.getEmail(),aesKey));

        if(user.getAge()==null){
            deUser.setAge("no information");
        }
        else{
            deUser.setAge(AesUtils.decrypt(user.getAge(),aesKey));
        }

        if(user.getAddress()==null){
            deUser.setAddress("no information");
        }
        else{
            deUser.setAddress(AesUtils.decrypt(user.getAddress(),aesKey));
        }

        model.addAttribute("user",deUser);
        return "personalMain";
    }

    @RequestMapping("/changePassword")
    public String changePassword(){
        return "changePassword";
    }
}
