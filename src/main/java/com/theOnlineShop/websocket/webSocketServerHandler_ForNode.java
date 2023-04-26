package com.theOnlineShop.websocket;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Tommy
 */

@Slf4j
//@ConditionalOnClass(value = WebSocketConfig.class)
@Component
@ServerEndpoint(value="/Node/{account}/{pass}")
public class webSocketServerHandler_ForNode {

    /**
     * 与某个客户端的连接对话，需要通过它来给客户端发送消息
     */
    private Session session;

    /**
     * 标识当前连接客户端的用户名
     */
    private String address;

    private String register;

    private String date;

    private static ConcurrentHashMap<String, webSocketServerHandler_ForNode> webSocketSet = new ConcurrentHashMap<>();

    private NodeInstance node;

    @PostConstruct
    void init(){
        //date
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        this.date=formatter.format(date);

        //todo test node instance
        this.node=new NodeInstance("192.4.4.5",this.date);
        chipInstance chipA=new chipInstance("tommy","127.0.0.4");
        chipInstance chipB=new chipInstance("tony","127.0.1.34");
        node.chipInstanceList.add(chipA);
        node.chipInstanceList.add(chipB);
        node.registerName="nanan";


        //address
        this.address="121.2434.55.56:8082";

        webSocketSet.put(address,this);

        //another
        chipInstance chipC=new chipInstance("fuck","127.0.0.35");
        chipInstance chipD=new chipInstance("you","127.0.1.890");

        webSocketServerHandler_ForNode node=new webSocketServerHandler_ForNode();
        node.address="6r7367t8638";
        node.node=new NodeInstance("6r7367t8638",this.date);
        node.node.chipInstanceList.add(chipC);
        node.node.chipInstanceList.add(chipD);
        node.node.registerName="lalal";

        webSocketSet.put("6r7367t8638",node);
    }

    /**
     * 连接建立成功调用的方法
     * session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen
    public void OnOpen(Session session, @PathParam(value = "account") String account, @PathParam(value = "pass") String pass){
        log.info("----------------------------------");

        //todo check register account and pass
        if(false){

            return;
        }

        this.address=WebsocketUtil.getRemoteAddress(session).toString().replace("/","");
        this.session = session;
        this.register=account;
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        this.date=formatter.format(date);
        // name是用来表示唯一客户端，如果需要指定发送，需要指定发送通过name来区分
        webSocketSet.put(this.address,this);
        //新建节点，节点实例可以保存每个芯片实体的状态
        node=new NodeInstance(address,this.date);
        node.registerName=account;
        log.info("接收到一个节点请求，地址:{}，账户:{}，密码:{}", address,account,pass);
        log.info("[WebSocket Node] 连接成功, 当前连接人数为:={}",webSocketSet.size());
        log.info("----------------------------------");
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void OnClose(){
        webSocketSet.remove(this.address);
        log.info("[WebSocket Node] 退出成功，当前连接人数为：={}",webSocketSet.size());
    }

    /**
     * 收到客户端消息后调用的方法
     */
    @OnMessage
    public void OnMessage(Session session,String message) {
        //log.info("[WebSocket Node] 收到消息：{}",message);
        JSONObject js=JSONObject.parseObject(message);
        log.info(js.getString("order"));
    }

    /**
     * 发生错误时调用
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error){
        log.info("[WebSocket Node] 发生错误: {}",error.getMessage());
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
