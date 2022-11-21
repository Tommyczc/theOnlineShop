package com.theOnlineShop.mapper;

import com.theOnlineShop.domain.userEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface userListMapper {
    public List<userEntity> loginByPasswordAndUserName(userEntity user);
    public int insertUser(userEntity user);
    public List<userEntity> selectlistByEmail(userEntity user);
    public List<userEntity> selectListByUserNameAndEmail(userEntity user);
    public List<userEntity> selectListByUserName(userEntity user);
}
