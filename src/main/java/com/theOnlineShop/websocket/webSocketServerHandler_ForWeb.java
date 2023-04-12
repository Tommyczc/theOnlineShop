package com.theOnlineShop.websocket;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@ServerEndpoint("/Web/{account}")
public class webSocketServerHandler_ForWeb {

    /**
     * 与某个客户端的连接对话，需要通过它来给客户端发送消息
     */
    private Session session;

    /**
     * 标识当前连接客户端的用户名
     */
    private String name;

    private static ConcurrentHashMap<String, webSocketServerHandler_ForWeb> webSocketSet = new ConcurrentHashMap<>();


    @OnOpen
    public void OnOpen(Session session,@PathParam(value = "account") String account){
        log.info("[webSocket] a new connection, session id: {}\ncurrent shiro session id: {}",session.getId(), SecurityUtils.getSubject().getSession().getId());
        this.name=account;
        this.session=session;
        webSocketSet.put(account,this);

        JSONObject js=new JSONObject();
        js.put("order","update");
        js.put("msg",webSocketServerHandler_ForNode.getAllNode());
        js.put("onlineNumber",webSocketSet.size());
        AppointSending(this.name,js.toJSONString());
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void OnClose(){
        webSocketSet.remove(this.name);
        log.info("[WebSocket] 退出成功，当前连接人数为：={}",webSocketSet.size());
    }

    /**
     * 收到客户端消息后调用的方法
     */
    @OnMessage
    public void OnMessage(String message) {
        log.info("[WebSocket] 收到消息：{}",message);
    }

    @OnError
    public void onError(Session session, Throwable error){
        log.info("[WebSocket] 发生错误: {}",error.getMessage());
    }

    @OnClose
    public void onClose(){
        webSocketSet.remove(this.name);
    }

    public static void AppointSending(String name,String message){
        try {
            webSocketSet.get(name).session.getBasicRemote().sendText(message);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void GroupSending(String message){
        for (String name : webSocketSet.keySet()){
            try {
                webSocketSet.get(name).session.getBasicRemote().sendText(message);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
