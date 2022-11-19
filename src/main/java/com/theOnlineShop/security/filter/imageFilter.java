package com.theOnlineShop.security.filter;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.regex.Pattern;

public class imageFilter extends AccessControlFilter {
    // 未登录登陆返状态回码

    public imageFilter(){}

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse,
                                      Object mappedValue) throws Exception {
        Subject subject = SecurityUtils.getSubject();

        // 这里配合APP需求我只需要做登录检测即可
        if (subject != null && subject.isAuthenticated()) {
            // TODO 登录检测通过，这里可以添加一些自定义操作
            String userName=subject.getPrincipal().toString();
            boolean isAdmin=subject.hasRole("admin");
            String url=getPathWithinApplication(servletRequest);

            if(isAdmin){
                System.out.println("Admin "+userName+", is looking images");
                //System.out.println("image url: "+url);
                return true;
            }
            else if(Pattern.matches("/image/"+userName+"/.*",url)){
                System.out.println("User "+userName+", is looking images");
                return true;
            }

            System.out.println("User "+userName+", has no right looking images");
            return false;
        }
        // 登录检测失败返货False后会进入下面的onAccessDenied()方法
        return false;
    }
    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest,
                                     ServletResponse servletResponse) throws Exception {

        return false;
    }
}