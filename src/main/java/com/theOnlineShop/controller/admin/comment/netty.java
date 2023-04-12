package com.theOnlineShop.controller.admin.comment;

import com.alibaba.fastjson.JSONObject;
import com.theOnlineShop.websocket.NodeInstance;
import com.theOnlineShop.websocket.webSocketServerHandler_ForNode;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;

@Controller
@RequiresRoles(value = {"admin","superAdmin"}, logical = Logical.OR)
public class netty {
    @RequestMapping("/admin/getSocketConnection")
    @ResponseBody
    private JSONObject getSocketConnection(){
        JSONObject js =new JSONObject();
        js.put("SocketConnection","allow");
        js.put("userName", SecurityUtils.getSubject().getPrincipals().toString());
        return js;
    }

}
