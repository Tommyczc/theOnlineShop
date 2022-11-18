package com.theOnlineShop.controller.pathInterceptor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/theOnlineShop")
public class homeController {

    @RequestMapping("/welcomePage")
    public String toWelcomePage(){
        return "welcome";
    }
}
