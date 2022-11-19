package com.theOnlineShop.security.encryption;

import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

public class Md5Utils {
    public static String encrypt(String content){
        return DigestUtils.md5DigestAsHex(content.getBytes());
    }
}
