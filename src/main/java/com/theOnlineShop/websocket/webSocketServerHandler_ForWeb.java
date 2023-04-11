package com.theOnlineShop.websocket;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Component;

import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@Slf4j
@Component
@ServerEndpoint("/TheOnlineShop")
public class webSocketServerHandler_ForWeb {
    @OnOpen
    public void OnOpen(Session session){
        log.info("[webSocket] a new connection, session id: {}\ncurrent shiro session id: {}",session.getId(), SecurityUtils.getSubject().getSession().getId());
    }
}
