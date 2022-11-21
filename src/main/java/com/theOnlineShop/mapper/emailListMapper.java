package com.theOnlineShop.mapper;

import com.theOnlineShop.domain.emailVerificationEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface emailListMapper {
    public List<emailVerificationEntity> selectListAll();
    public List<emailVerificationEntity> selectListByEmail(emailVerificationEntity email);
    public int insertVerificationCode(emailVerificationEntity email);
    public int deleteVerificationCode(emailVerificationEntity email);
    public int updateVerificationCode(emailVerificationEntity email);
    public List<emailVerificationEntity> selectListByEmailAndCode(emailVerificationEntity email);
}
