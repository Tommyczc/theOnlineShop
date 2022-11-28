package com.theOnlineShop.controller.pathInterceptor;

import com.theOnlineShop.controller.system.domain.versionControllerDomain;
import com.theOnlineShop.domain.userEntity;
import com.theOnlineShop.security.encryption.AesUtils;
import com.theOnlineShop.service.userListInter;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.AesCipherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/theOnlineShop")
public class homeController {
    @Value("${theOnlineShop.Github}")
    private String gitHub;
    @Value("${theOnlineShop.Name}")
    private String name;
    @Value("${theOnlineShop.Version}")
    private String version;
    @Value("${theOnlineShop.Copyright}")
    private String copyright;
    @Autowired
    private userListInter userService;
    @Value("${personal.EncryptionKey.aes-key}")
    private String aesKey;

    @RequestMapping("/welcomePage")
    public String toWelcomePage(Model model){
        model.addAttribute("version",new versionControllerDomain(gitHub,name,version,copyright));

        userEntity user=new userEntity();
        user.setUserName(AesUtils.encrypt(SecurityUtils.getSubject().getPrincipals().toString(),aesKey));
        List<userEntity> userList=userService.getUserInformation(user);
        //解密
        userEntity deUser=new userEntity();
        deUser.setUserName(AesUtils.decrypt(userList.get(0).getUserName(),aesKey));
        if(userList.get(0).getHeadSculpture()!=null){
            deUser.setHeadSculpture(AesUtils.decrypt(userList.get(0).getHeadSculpture(),aesKey));
        }

        else{
            //加入默认头像
            deUser.setHeadSculpture("/image/avatar/user.png");
        }
        model.addAttribute("userInformation",deUser);
        //role
        return "index";
    }
}
