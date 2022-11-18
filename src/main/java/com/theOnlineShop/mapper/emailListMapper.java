package com.theOnlineShop.mapper;

import com.theOnlineShop.domain.emailVerificationEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface emailListMapper {
    public List<emailVerificationEntity> selectListAll();
    public List<emailVerificationEntity> selectListByUserNameAndEmail(emailVerificationEntity email);
    public void insertVerificationCode(emailVerificationEntity email);
}
