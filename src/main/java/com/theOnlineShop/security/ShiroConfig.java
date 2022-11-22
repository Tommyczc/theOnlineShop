package com.theOnlineShop.security;

import com.theOnlineShop.security.filter.imageFilter;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.servlet.Filter;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    /**
     * Shiro自带的过滤器，可以在这里配置拦截页面
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Autowired DefaultWebSecurityManager securityManager) {

        //初始化一个ShiroFilter工程类
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        Map<String, Filter> filterObjMap = new LinkedHashMap<>();
        filterObjMap.put("imageFilter", new imageFilter()); //匿名访问静态资源
        shiroFilterFactoryBean.setFilters(filterObjMap);

        //Shiro是通过SecurityManager来管理整个认证和授权流程的，这个SecurityManager可以在下面初始化
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        Map<String, String> filterMap = new LinkedHashMap<>();

         //Shiro过滤器常用的有如下几种
        filterMap.put("/login", "anon");
        filterMap.put("/fail", "anon");

        //filterMap.put("/api/user/notics", "authc");
        //注意，roles[user,admin]对应的用户是 《同时》 拥有这两种身份的用户
        filterMap.put("/admin/**", "roles[admin]");
        filterMap.put("/guest/**", "authc");
        filterMap.put("/theOnloneShow/**","authc");
        filterMap.put("/image/**", "imageFilter"); //匿名访问静态资源
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);

        //配置登录页
        shiroFilterFactoryBean.setLoginUrl("/login");
        return shiroFilterFactoryBean;
    }

    @Bean(name = "lifecycleBeanPostProcessor")
    public static LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
     */
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);
        return creator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }



    /**
     * SecurityManager管理认证、授权整个流程
     */
    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager(@Autowired UserRealm userRealm) {

        //新建一个SecurityManager供ShiroFilterFactoryBean使用
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //SecurityManager既然管理认证等信息，那他就需要一个类来帮他查数据库吧。这就是Realm类
        securityManager.setRealm(userRealm);
        //System.out.println("userRealm state:  "+userRealm);
        return securityManager;
    }

    @Bean
    public CookieRememberMeManager rememberMeManager(){
        // logger.info("注入Shiro的记住我(CookieRememberMeManager)管理器-->rememberMeManager", CookieRememberMeManager.class);
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        //rememberme cookie加密的密钥 建议每个项目都不一样 默认AES算法 密钥长度（128 256 512 位），通过以下代码可以获取
        KeyGenerator keygen = null;
        try {
            keygen = KeyGenerator.getInstance("AES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        SecretKey deskey = keygen.generateKey();
        //System.out.println(Base64.encodeToString(deskey.getEncoded()));
        byte[] cipherKey = Base64.decode(Base64.encodeToString(deskey.getEncoded()));
        cookieRememberMeManager.setCipherKey(cipherKey);
        cookieRememberMeManager.setCookie(rememberMeCookie());
        return cookieRememberMeManager;
    }
    @Bean
    public SimpleCookie rememberMeCookie(){
        //这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        //如果httyOnly设置为true，则客户端不会暴露给客户端脚本代码，使用HttpOnly cookie有助于减少某些类型的跨站点脚本攻击；
        simpleCookie.setHttpOnly(true);
        //记住我cookie生效时间,默认5天 ,单位秒：60 * 60 * 24 * 30
        simpleCookie.setMaxAge(60 * 60 * 24 * 5);
        //simpleCookie.setMaxAge(60*1);

        return simpleCookie;
    }

    /**
     * 自定义Realm，当SecurityBean需要来查询相关权限信息时，需要有Realm代劳
     */
    @Bean
    public UserRealm userRealm() {
        return new UserRealm();
    }
}
