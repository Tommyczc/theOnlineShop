package com.theOnlineShop.controller.pathInterceptor;

import com.theOnlineShop.controller.system.domain.versionControllerDomain;
import com.theOnlineShop.domain.userEntity;
import com.theOnlineShop.fileStore.fileUpload;
import com.theOnlineShop.security.encryption.AesUtils;
import com.theOnlineShop.service.userListInter;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/theOnlineShop")
public class homeController {
    @Autowired
    private userListInter userService;
    @Value("${personal.EncryptionKey.aes-key}")
    private String aesKey;
    @Autowired
    private versionControllerDomain version;

    @Autowired
    private fileUpload fileUtils;

    @Value("${personal.fileStore.mappingAddress}")
    private String mappingAddress;

    @RequestMapping("/welcomePage")
    public String toWelcomePage(Model model){
        model.addAttribute("version",version);

        userEntity user=new userEntity();
        user.setUserName(AesUtils.encrypt(SecurityUtils.getSubject().getPrincipals().toString(),aesKey));
        userEntity theUser=userService.getUserInformation(user);
        //解密
        userEntity deUser=new userEntity();
        deUser.setUserName(AesUtils.decrypt(theUser.getUserName(),aesKey));
        if(theUser.getHeadSculpture()!=null && !theUser.getHeadSculpture().isEmpty()){
            deUser.setHeadSculpture(mappingAddress+"/"+SecurityUtils.getSubject().getPrincipals().toString()+"/"+"avatar/"+AesUtils.decrypt(theUser.getHeadSculpture(),aesKey));
        }
        else{
            //加入默认头像
            deUser.setHeadSculpture("/image/avatar/user.png");
        }

        model.addAttribute("userInformation",deUser);

        //System.out.println(SecurityUtils.getSubject().getPrincipal().toString());
        return "index";
    }
}
