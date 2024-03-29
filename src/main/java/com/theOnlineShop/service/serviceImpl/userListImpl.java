package com.theOnlineShop.service.serviceImpl;

import com.theOnlineShop.domain.userEntity;
import com.theOnlineShop.mapper.userListMapper;
import com.theOnlineShop.redis.RedisService.RedisUserListInter;
import com.theOnlineShop.service.userListInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class userListImpl implements userListInter {
    @Autowired
    userListMapper userMapper;

    @Autowired
    RedisUserListInter redisUserMapper;

    @Value("${personal.EncryptionKey.aes-key}")
    private String aesKey;

    @Override
    public boolean login(userEntity user) {
        userEntity theUser=null;
        try {
            theUser= redisUserMapper.getLoginInf(user.getUserName());
        }catch(Exception e){
            System.out.println(e.hashCode());
            //return true;
        }

        if ( theUser!= null) {
            //theUser = (userEntity) redisUserMapper.getLoginInf(user.getUserName());
            //System.out.println("redis find a user with same user name: userName:"+theUser.getUserName()+" password:"+theUser.getPassword());

            if (user.getUserName().equals(theUser.getUserName()) && user.getPassword().equals(theUser.getPassword())) {
                //System.out.println("redis find user");
                return true;
            }
            return false;
        } else {
            List<userEntity> userList = userMapper.loginByPasswordAndUserName(user);

            if (userList.size() >= 1) {
                theUser = userList.get(0);
                if (user.getUserName().equals(theUser.getUserName()) && user.getPassword().equals(theUser.getPassword())) {
                    // add the user result to redis
                    redisUserMapper.setLoginInfo(theUser);
                    return true;
                }
                //return false;
            }
            return false;
        }

    }

    /**
     * 检测数据库该邮箱是否用于其他账号的邮箱
     */
    @Override
    public boolean emailDuplicateCheck(userEntity user) {
        List<userEntity> userList=userMapper.selectlistByEmail(user);
        if(userList.size()==0){
            return true;
        }
        return false;
    }

    @Override
    public boolean userNameDuplicateCheck(userEntity user) {
        List<userEntity> userList=userMapper.selectListByUserName(user);
        if(userList.size()==0){return true;}
        return false;
    }

    @Override
    public boolean register(userEntity user) {
        int i=userMapper.insertUser(user);
        if(i==1){return true;}
        return false;
    }

    @Override
    public userEntity getUserInformation(userEntity user) {
        userEntity theUser = null;
        try {
            if (redisUserMapper.getLoginInf(user.getUserName()) != null) {
                theUser= redisUserMapper.getLoginInf(user.getUserName());
            }
            else{
                theUser=userMapper.selectListByUserName(user).get(0);
                redisUserMapper.setLoginInfo(theUser);
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return theUser;
        //return userMapper.selectListByUserName(user);
    }

    @Override
    public boolean uploadAvatar(userEntity user) {
        int isUpload=userMapper.updateUserAvatar(user);
        if(isUpload==1){
            redisUserMapper.deleteUser(user);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateUserInformation(userEntity user) {
        int i=userMapper.updateUserAgeAndAddress(user);
        if(i==1){
            redisUserMapper.deleteUser(user);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateUserPassword(userEntity user) {
        if(userMapper.updateUserPassword(user)==1){
            redisUserMapper.deleteUser(user);
            return true;
        }
        return false;
    }
}
