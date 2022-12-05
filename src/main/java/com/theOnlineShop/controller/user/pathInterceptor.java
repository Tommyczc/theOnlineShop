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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Controller
@RequestMapping("/user")
@RequiresRoles(value = {"user","admin","superAdmin"}, logical = Logical.OR)
public class pathInterceptor {

    @Autowired
    private baseInformation information;
    @RequestMapping("/personalPage")
    public String personalInformation(Model model){
        information.getPersonalInformationLogic(model);
        return "user/personalMain";
    }

    @RequestMapping("/changePassword")
    public String changePassword(){
        return "user/changePassword";
    }
}
