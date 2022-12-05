package com.theOnlineShop.security.filter;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public class customizeFilter extends AccessControlFilter {
    private String prefixUrl;
    private String suffixUrl;
    private boolean withUserName;
    private Set<String> otherViewer;

    public customizeFilter(String prefixUrl,String suffixUrl,boolean withUserName,Set<String> otherViewer){
        this.prefixUrl=prefixUrl;
        this.suffixUrl=suffixUrl;
        this.withUserName=withUserName;
        this.otherViewer=otherViewer;
    }

    public customizeFilter(String prefixUrl){
        this(prefixUrl, null,false,null);
    }

    public customizeFilter(String prefixUrl,boolean withUserName){
        this(prefixUrl,null,withUserName,null);
    }


    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse,
                                      Object mappedValue) throws Exception {
        Logger logger = LoggerFactory.getLogger(getClass());
        Subject subject = SecurityUtils.getSubject();

        // 这里配合APP需求我只需要做登录检测即可
        if (subject != null && subject.isAuthenticated()) {
            // TODO 登录检测通过，这里可以添加一些自定义操作
            String userName=subject.getPrincipal().toString();
            boolean isAllowed=false;

           if(otherViewer!=null){
               for(String role:otherViewer){
                   isAllowed=subject.hasRole(role);
               }
           }

            String url=getPathWithinApplication(servletRequest);

            if(isAllowed){
                logger.info("User "+subject.getPrincipal()+" is allow to access the document");
                return true;
            }
            else if(withUserName){


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
