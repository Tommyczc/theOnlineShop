package com.theOnlineShop.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Tommy
 */

@Slf4j
@Component
@ServerEndpoint("/Node/{account}/{pass}")
public class webSocketServerHandler_ForNode {

    /**
     * 与某个客户端的连接对话，需要通过它来给客户端发送消息
     */
    private Session session;

    /**
     * 标识当前连接客户端的用户名
     */
    private String name;

    private static ConcurrentHashMap<String, webSocketServerHandler_ForNode> webSocketSet = new ConcurrentHashMap<>();

    private NodeInstance node;

    /**
     * 连接建立成功调用的方法
     * session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen
    public void OnOpen(Session session, @PathParam(value = "account") String account, @PathParam(value = "pass") String pass){
        log.info("----------------------------------");
        log.info("接收到一个节点请求，地址:{}，账户:{}，密码:{}", WebsocketUtil.getRemoteAddress(session).toString(),account,pass);
        this.session = session;
        this.name = WebsocketUtil.getRemoteAddress(session).toString();
        // name是用来表示唯一客户端，如果需要指定发送，需要指定发送通过name来区分
        webSocketSet.put(name,this);
        //新建节点，节点实例可以保存每个芯片实体的状态
        node=new NodeInstance(name);
        log.info("[WebSocket] 连接成功, 当前socket ip:{}, 当前连接人数为:={}",WebsocketUtil.getRemoteAddress(session).toString(),webSocketSet.size());
        log.info("----------------------------------");

        //AppointSending(this.name,);
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

    /**
     * 发生错误时调用
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error){
        log.info("[WebSocket] 发生错误: {}",error.getMessage());
    }

    @OnClose
    public void onClose(){
        webSocketSet.remove(this.name);
    }

    /**
     * 群发
     * @param message
     */
    public static void GroupSending(String message){
        for (String name : webSocketSet.keySet()){
            try {
                webSocketSet.get(name).session.getBasicRemote().sendText(message);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     * 指定发送
     * @param name
     * @param message
     */
    public static void AppointSending(String name,String message){
        try {
            webSocketSet.get(name).session.getBasicRemote().sendText(message);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //todo a static method can get all node instances information
    public static ArrayList<NodeInstance> getAllNode(){
        ArrayList<NodeInstance> instanceList=new ArrayList<>();
        for(webSocketServerHandler_ForNode handler:webSocketSet.values()){
            instanceList.add(handler.node);
        }
        return instanceList;
    }
}
