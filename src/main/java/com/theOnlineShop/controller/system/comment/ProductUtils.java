package com.theOnlineShop.controller.system.comment;

import com.theOnlineShop.vo.httpReponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/system")
@Controller
public class ProductUtils {

    @RequestMapping("/uploadProduct/upload")
    @ResponseBody
    public httpReponseEntity toUploadProduct(@RequestParam(value="name",required = true) String name){



        return new httpReponseEntity("warning", "fuck it");
    }

}
