package com.theOnlineShop.mapper;

import com.theOnlineShop.domain.emailVerificationEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface emailListMapper {
    public List<emailVerificationEntity> selectListAll();
    public List<emailVerificationEntity> selectListByEmail(emailVerificationEntity email);
    public void insertVerificationCode(emailVerificationEntity email);
    public void deleteVerificationCode(emailVerificationEntity email);
    public void updateVerificationCode(emailVerificationEntity email);
}
