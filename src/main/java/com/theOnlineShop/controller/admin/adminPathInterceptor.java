package com.theOnlineShop.controller.admin;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller

@RequestMapping("/admin")
@RequiresRoles(value = {"admin","superAdmin"}, logical = Logical.OR)
public class adminPathInterceptor {
    @RequestMapping("/nettyDemo")
    public String nettyDemo(){
        return "admin/nettyDemo";
    }

    @RequestMapping("/roleChange")
    public String roleChange(){
        return "admin/roleChange";
    }
}
