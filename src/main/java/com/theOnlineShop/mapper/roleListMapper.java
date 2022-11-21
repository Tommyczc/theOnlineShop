package com.theOnlineShop.mapper;

import com.theOnlineShop.domain.roleEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface roleListMapper {
    public int insertRole(roleEntity role);
    public int deleteRole(roleEntity role);
    public int updateRole(roleEntity role);
    public List<roleEntity> selectListByUserName(roleEntity role);
    public List<roleEntity> selectListAll();
    public List<roleEntity> selectListByRoleName(roleEntity role);
}
