package com.theOnlineShop.controller.system.controller;

import com.theOnlineShop.controller.system.domain.versionControllerDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/system")
@Controller
public class pathController {
    @Autowired
    private versionControllerDomain version;
    @RequestMapping("/main")
    public String toMain(Model model){
        model.addAttribute("versionController",version);
        return "main";
    }

    @RequestMapping("/viewProduct")
    public String toViewProduct(){
        return "system/viewProduct";
    }

    @RequestMapping("/uploadProduct")
    public String toUploadProduct(){
        return "system/uploadProduct";
    }

    @RequestMapping("/purchaseHistory")
    public String toPurchaseHistory(){
        return "system/purchaseHistory";
    }

    @RequestMapping("/uploadHistory")
    public String toUploadHistory(){
        return "system/uploadHistory";
    }
}
