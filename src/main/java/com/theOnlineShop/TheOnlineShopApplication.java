package com.theOnlineShop;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.theOnlineShop.mapper")
public class TheOnlineShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(TheOnlineShopApplication.class, args);
    }

}
