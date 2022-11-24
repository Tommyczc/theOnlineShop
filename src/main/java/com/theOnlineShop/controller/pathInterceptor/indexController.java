package com.theOnlineShop.controller.pathInterceptor;

import com.theOnlineShop.controller.system.domain.versionControllerDomain;
import com.theOnlineShop.domain.userEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class indexController {

    @Value("${theOnlineShop.Github}")
    private String gitHub;
    @Value("${theOnlineShop.Name}")
    private String name;
    @Value("${theOnlineShop.Version}")
    private String version;
    @Value("${theOnlineShop.Copyright}")
    private String copyright;

    @RequestMapping(value={"/login","/"})
    public String toLogin(Model model){
        model.addAttribute("version",new versionControllerDomain(gitHub,name,version,copyright));
        model.addAttribute("userEntity", new userEntity());
        return "login";
    }

    @RequestMapping("/register")
    public String toRegister(Model model){
        model.addAttribute("version",new versionControllerDomain(gitHub,name,version,copyright));
        return "register";
    }
}
