package com.theOnlineShop;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableCaching
@MapperScan("com.theOnlineShop.mapper")
@EnableTransactionManagement
public class TheOnlineShopApplication {
    public static void main(String[] args) {
        SpringApplication.run(TheOnlineShopApplication.class, args);
    }
}
