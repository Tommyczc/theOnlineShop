package com.theOnlineShop.controller.user.comment;

import com.theOnlineShop.fileStore.fileUpload;
import com.theOnlineShop.vo.httpReponseEntity;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Controller
@RequestMapping("/user")
@RequiresRoles(value = {"user","admin","superAdmin"}, logical = Logical.OR)
public class utils {
    @Autowired
    private fileUpload fileUtils;

    @RequestMapping(value = ("/avatarUpload"),method = RequestMethod.POST)
    @ResponseBody
    public httpReponseEntity uploadAvatar(@RequestParam(value="avatar",required = true) MultipartFile file){
        boolean isStore=fileUtils.recoverAvatar(file);
        if(!isStore){
            return new httpReponseEntity("warm","头像上传失败");
        }
        return new httpReponseEntity("success","头像上传成功");
    }
}
