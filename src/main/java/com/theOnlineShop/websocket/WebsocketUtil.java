package com.theOnlineShop.websocket;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.SocketChannel;
import javax.websocket.RemoteEndpoint.Async;
import javax.websocket.Session;

/**
 * @author tommy
 */
@Slf4j
public class WebsocketUtil {

    public static SocketAddress getRemoteAddress(Session session) {
        if (session == null) {
            log.error("[WebSocket] The session is null");
            return null;
        }
        Async async = session.getAsyncRemote();

        if(async==null){
            log.error("[WebSocket] The session.async is null");
            return null;
        }
        //在Tomcat 8.0.x版本有效
		//InetSocketAddress addr = (InetSocketAddress) getFieldInstance(async,"base#sos#socketWrapper#socket#sc#remoteAddress");
        //在Tomcat 8.5以上版本有效
        //Object addrObj = getFieldInstance(async,"base#socketWrapper#socket#sc#remoteAddress");
        SocketChannel chanel=(SocketChannel) getFieldInstance(async,"base#socketWrapper#socket#sc");
        SocketAddress addr=chanel.socket().getRemoteSocketAddress();

        //log.info("host address: {}",chanel.socket().getLocalAddress().getHostAddress());

        return addr;
    }

    private static Object getFieldInstance(Object obj, String fieldPath) {
        String fields[] = fieldPath.split("#");
        for (String field : fields) {
            obj = getField(obj, obj.getClass(), field);
            //log.info("[WebSocket] Find new field: {}, object state: {}",field,obj);
            if (obj == null) {
                log.error("[WebSocket] The object is null, at field: {}",field);
                return null;
            }
        }

        return obj;
    }

    private static Object getField(Object obj, Class<?> clazz, String fieldName) {
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                Field field;
                field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                return field.get(obj);
            } catch (Exception e) {
            }
        }

        return null;
    }

}