package com.theOnlineShop.security.filter;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class webSocketFilter extends AccessControlFilter {
    private String prefixUrl;
    private ArrayList<String> allowRoles;
    private Logger logger;

    public webSocketFilter(String prefixUrl, ArrayList<String> allowRoles) {
        this.prefixUrl = prefixUrl;
        this.allowRoles = allowRoles;
        logger = LoggerFactory.getLogger(getClass());
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        Subject subject = SecurityUtils.getSubject();
        String url=getPathWithinApplication(servletRequest);
        boolean isAdmin=false;
        for(String role:allowRoles){
            if(subject.hasRole(role)){
                isAdmin=true;
                break;
            }
        }

        if (subject != null && subject.isAuthenticated()) {
            String userName=subject.getPrincipal().toString();
            if (Pattern.matches(prefixUrl + userName, url) && isAdmin) {
                return true;
            } else if (!isAdmin) {
                logger.warn("The user is not admin or superAdmin, not allow to connect websocket");
                return false;
            } else if (!Pattern.matches(prefixUrl + userName, url)) {
                logger.warn("The user name on websocket url does not match current user account");
                return false;
            }
        }
        else{
            logger.warn("User is not authenticated");
        }

        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        return false;
    }
}
