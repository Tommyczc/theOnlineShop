package com.theOnlineShop.controller.system.comment;

import com.theOnlineShop.vo.httpReponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

public class ProductUtils {

    @RequestMapping("/uploadProduct/upload")
    @ResponseBody
    public httpReponseEntity toUploadProduct(){

        return new httpReponseEntity("warning", "fuck it");
    }

}
