package com.theOnlineShop.controller.pathInterceptor;

import com.theOnlineShop.domain.userEntity;
import com.theOnlineShop.security.emailVerification.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
