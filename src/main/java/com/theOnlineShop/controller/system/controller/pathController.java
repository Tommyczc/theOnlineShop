package com.theOnlineShop.controller.system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/system")
@Controller
public class pathController {
    @RequestMapping("/main")
    public String toMain(){
        return "main";
    }
}
