package com.theOnlineShop.controller.admin.comment;

import com.theOnlineShop.websocket.NodeInstance;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;

@Controller
public class netty {
    @RequestMapping("/admin/getAllNodes")
    @ResponseBody
    private List<NodeInstance> getAllNode(){

        return null;
    }

}
