package com.theOnlineShop.controller;

import com.theOnlineShop.domain.userEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class indexController {

    @RequestMapping(value={"/login","/"})
    public String toLogin(Model model){
        model.addAttribute("userEntity",new userEntity());
        return "index";
    }

    @RequestMapping("/register")
    public String toRegister(){
        return "register";
    }
}
