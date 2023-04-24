package com.theOnlineShop.controller.admin.comment;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;

@Slf4j
@Controller
@RequiresRoles(value = {"admin","superAdmin"}, logical = Logical.OR)
public class netty {

    @Autowired
    private Environment environment;

    private static String localIp;
    private static String port;

    @PostConstruct
    void Init(){
        netty.localIp=getLocalAddress();
        netty.port=environment.getProperty("server.port");

        log.info("*****************");
        log.info("Current V4IP: {}", localIp);
        log.info("Current Port: {}",port);
        log.info("*****************");
    }

    @RequestMapping("/admin/getSocketConnection")
    @ResponseBody
    private JSONObject getSocketConnection(){
        JSONObject js =new JSONObject();
        js.put("SocketConnection","allow");
        js.put("userName", SecurityUtils.getSubject().getPrincipals().toString());
        js.put("ip",localIp);
        js.put("port",port);
        log.info("User {} try to get websocket connection information, auth: {}",SecurityUtils.getSubject().getPrincipals().toString(),SecurityUtils.getSubject().isAuthenticated());
        return js;
    }

    public static String getV4IP(){
        String ip = "http://pv.sohu.com/cityjson?ie=utf-8";
        String inputLine = "";
        String read = "";
        String toIp = "";
        try {
            URL url = new URL(ip);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            while ((read = in.readLine()) != null) {
                inputLine += read;
            }
            String ObjJson = inputLine.substring(inputLine.indexOf("=") + 1, inputLine.length() - 1);
            JSONObject jsonObj = JSON.parseObject(ObjJson);
            toIp = jsonObj.getString("cip");
        } catch (Exception e) {
            toIp = "";
        }
        return toIp;
    }

    public static String getLocalAddress(){
        String ip = "";
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return ip;
    }
}
