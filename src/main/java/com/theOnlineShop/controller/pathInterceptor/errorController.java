package com.theOnlineShop.controller.pathInterceptor;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error")
public class errorController {

    @RequestMapping("/403")
    public String error403(Model model){
        Subject subject=SecurityUtils.getSubject();
        if(!subject.isAuthenticated()){model.addAttribute("failInf","Exception 403: "+"sorry, please login");}
        else{model.addAttribute("failInf","Exception 403:"+"User "+subject.getPrincipals()+", you do not have authorization to access this page");}
        return "UnauthorizedExceptionPage";
    }

    @RequestMapping("/401")
    public String error401(Model model){
        Subject subject=SecurityUtils.getSubject();
        if(!subject.isAuthenticated()){model.addAttribute("failInf","Exception 401: "+"sorry, please login");}
        else{model.addAttribute("failInf","Exception 401:"+"User "+subject.getPrincipals()+", you do not have authorization to access this page");}
        return "AuthorizedExceptionPage";
    }
}
