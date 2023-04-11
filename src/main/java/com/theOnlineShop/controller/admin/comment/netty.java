package com.theOnlineShop.controller.admin.comment;

import com.theOnlineShop.websocket.NodeInstance;
import com.theOnlineShop.websocket.webSocketServerHandler_ForNode;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;

@Controller
@RequiresRoles(value = {"admin","superAdmin"}, logical = Logical.OR)
public class netty {
    @RequestMapping("/admin/")
    @ResponseBody
    private ArrayList<NodeInstance> getAllNode(){
        return webSocketServerHandler_ForNode.getAllNode();
    }

}
