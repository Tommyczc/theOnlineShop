package com.theOnlineShop.security;

import com.theOnlineShop.domain.permissionEntity;
import com.theOnlineShop.domain.roleEntity;
import com.theOnlineShop.domain.userEntity;
import com.theOnlineShop.security.encryption.AesUtils;
import com.theOnlineShop.security.encryption.Md5Utils;
import com.theOnlineShop.service.permissionListInter;
import com.theOnlineShop.service.roleListInter;
import com.theOnlineShop.service.userListInter;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

class UserRealm extends AuthorizingRealm {
    @Autowired
    private userListInter userMapper;
    @Value("${personal.EncryptionKey.aes-key}")
    private String aesKey;
    @Autowired
    private permissionListInter permissionMapper;
    @Autowired
    private roleListInter roleMapper;


    //前面被authc拦截后，需要认证，SecurityBean会调用这里进行认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        Logger logger = LoggerFactory.getLogger(getClass());
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String unEncryptPass=String.copyValueOf(token.getPassword());
        //登录验证
        //很尴尬的发现token里面的密码是char[]格式，toString函数只能返回数列的哈希值，并不能直接转化成String
        userEntity user = new userEntity(AesUtils.encrypt(token.getUsername(),aesKey), Md5Utils.encrypt(String.copyValueOf(token.getPassword())));
        boolean login=userMapper.login(user);

        //System.out.println(String.copyValueOf(token.getPassword()));
        logger.info(token.getUsername()+" login state:   "+login);

        if (!login) {
            logger.warn(token.getUsername()+" login state:   "+login);
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

        //System.out.println("roles: "+roles);
        //System.out.println("permissions: "+permissions);

        return info;
    }


    //get the roles by username
    private Set<String> getRoleByUserName(String username){
        roleEntity role=new roleEntity();
        role.setUserName(AesUtils.encrypt(username,aesKey));
        List<roleEntity> roleList=roleMapper.findRole(role);
        Set<String> roleName=new HashSet<>();
        for(roleEntity theRole:roleList){
            roleName.add(theRole.getRoleName());
        }
        return roleName;
    }

    //get the permission by role name
    private Set<String> getPermissionByRoleName(String roleName){
        permissionEntity permission=new permissionEntity();
        permission.setRoleName(roleName);
        List<permissionEntity> permissionList=permissionMapper.findPermission(permission);
        Set<String> permissionName=new HashSet<>();
        for(permissionEntity thePermission:permissionList){
            permissionName.add(thePermission.getPermission());
        }
        return permissionName;
    }
}
