package com.theOnlineShop.controller.pathInterceptor;

import com.theOnlineShop.controller.system.domain.versionControllerDomain;
import com.theOnlineShop.domain.userEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class indexController {

    @Autowired
    private versionControllerDomain version;

    @RequestMapping(value={"/login","/"})
    public String toLogin(Model model){
        model.addAttribute("version",version);
        model.addAttribute("userEntity", new userEntity());
        return "login";
    }

    @RequestMapping("/register")
    public String toRegister(Model model){
        model.addAttribute("version",version);
        return "register";
    }
}
