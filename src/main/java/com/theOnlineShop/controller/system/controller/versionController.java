package com.theOnlineShop.controller.system.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @RequestMapping("/version")
    @ResponseBody
    public String getVersion() {
        return version;
    }
    @RequestMapping("/name")
    @ResponseBody
    public String getName() {
        return name;
    }
    @RequestMapping("/copyRight")
    @ResponseBody
    public String getCopyRight() {
        return copyRight;
    }
    @RequestMapping("/github")
    @ResponseBody
    public String getGithubLink() {
        return githubLink;
    }
}
