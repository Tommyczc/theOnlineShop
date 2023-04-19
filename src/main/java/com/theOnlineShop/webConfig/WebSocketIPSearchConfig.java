package com.theOnlineShop.webConfig;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;

import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @deprecated
 */
@Slf4j
public class WebSocketIPSearchConfig extends ServerEndpointConfig.Configurator {

    public static final String CLIENT_IP = "CLIENT_IP";

    @Override
    public void modifyHandshake(ServerEndpointConfig config, HandshakeRequest request, HandshakeResponse response) {
        String clientIP = null;
        List<String> list = null;
        final Map<String, List<String>> headers = request.getHeaders();
        log.debug("WebSocket头部：{}", headers);
        if (CollectionUtils.isNotEmpty((list = headers.get("x-real-ip")))) {
            clientIP = list.get(0);
        } else if (CollectionUtils.isNotEmpty((list = headers.get("forwarded")))) {
//    		[proto=http;host="192.168.8.187:9999";for="192.168.8.216:56671"]
            final String forwarded = list.get(0);
            final Pattern compile = Pattern.compile(".*for=\"(?<ip>.*):.*");
            final Matcher matcher = compile.matcher(forwarded);
            if (matcher.matches()) {
                clientIP = matcher.group("ip");
            }
        } else if (CollectionUtils.isNotEmpty((list = headers.get("x-forwarded-for")))) {
            clientIP = list.get(0);
        }
        final Map<String, Object> properties = config.getUserProperties();
        if (properties != null && clientIP != null) {
            properties.put(WebSocketIPSearchConfig.CLIENT_IP, clientIP);
        }
    }

}
