package com.theOnlineShop.controller.system.controller;

import com.theOnlineShop.controller.system.domain.versionControllerDomain;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/system/versionControl")
public class versionController {

    @Value("${theOnlineShop.Version}")
    private String version;
    @Value("${theOnlineShop.Name}")
    private String name;
    @Value("${theOnlineShop.Copyright}")
    private String copyRight;
    @Value("${theOnlineShop.Github}")
    private String githubLink;



}
