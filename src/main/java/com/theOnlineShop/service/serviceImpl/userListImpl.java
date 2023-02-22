package com.theOnlineShop.service.serviceImpl;

import com.theOnlineShop.domain.userEntity;
import com.theOnlineShop.mapper.userListMapper;
import com.theOnlineShop.redis.RedisService.RedisUserListInter;
import com.theOnlineShop.security.encryption.AesUtils;
import com.theOnlineShop.security.encryption.Md5Utils;
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
    RedisUserListInter redisUserListInter;

    @Value("${personal.EncryptionKey.aes-key}")
    private String aesKey;

    @Override
    public boolean login(userEntity user) {
        userEntity theUser;
        try {
            if (redisUserListInter.getLoginInf(user.getUserName()) != null) {
                theUser = (userEntity) redisUserListInter.getLoginInf(user.getUserName());
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
                        redisUserListInter.setLoginInfo(userList.get(0));
                        return true;
                    }
                    //return false;
                }
                return false;
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        finally {
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
    public List<userEntity> getUserInformation(userEntity user) {
        return userMapper.selectListByUserName(user);
    }

    @Override
    public boolean uploadAvatar(userEntity user) {
        int isUpload=userMapper.updateUserAvatar(user);
        if(isUpload==1){return true;}
        return false;
    }
}
