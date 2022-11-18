package com.theOnlineShop.shiroSecurity;

import com.theOnlineShop.domain.userEntity;
import com.theOnlineShop.service.userListInter;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

class UserRealm extends AuthorizingRealm {
    @Autowired
    private userListInter userMapper;


    //前面被authc拦截后，需要认证，SecurityBean会调用这里进行认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String unEncryptPass=String.copyValueOf(token.getPassword());
        //登录验证
        //很尴尬的发现token里面的密码是char[]格式，toString函数只能返回数列的哈希值，并不能直接转化成String
        userEntity user = new userEntity(token.getUsername(), String.copyValueOf(token.getPassword()));
        boolean login=userMapper.login(user);

        //System.out.println(String.copyValueOf(token.getPassword()));
        System.out.println(token.getUsername()+" login state:   "+login);

        if (!login) {
            System.out.println("not login");
            return null;
        } else {
            //System.out.println("login");
            return new SimpleAuthenticationInfo(token.getUsername(), token.getPassword(), "com.example.theweb.security.UserRealm");
        }
    }




    // 10. 前面被roles拦截后，需要授权才能登录，SecurityManager需要调用这里进行权限查询
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username=(String)principalCollection.getPrimaryPrincipal();
        Set<String> roles=getRoleByUserName(username);
        Set<String> permissions=new HashSet<>();


        for(String role:roles){
            Set<String> permission=getPermissionByRoleName(role);
            for(String per:permission)
            permissions.add(per);
        }

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(permissions);
        info.setRoles(roles);

        System.out.println("roles: "+roles);
        System.out.println("permissions: "+permissions);

        return info;
    }


    //get the roles by user name
    private Set<String> getRoleByUserName(String username){
        return null;
    }

    //get the permission by role name
    private Set<String> getPermissionByRoleName(String rolename){
        return null;
    }
}
