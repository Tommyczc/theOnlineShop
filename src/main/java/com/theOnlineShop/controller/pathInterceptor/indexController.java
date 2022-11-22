package com.theOnlineShop.controller.pathInterceptor;

import com.theOnlineShop.domain.userEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class indexController {


    @RequestMapping(value={"/login","/"})
    public String toLogin(Model model){
        model.addAttribute("userEntity",new userEntity());
        return "login";
    }

    @RequestMapping("/register")
    public String toRegister(){
        return "register";
    }
}
