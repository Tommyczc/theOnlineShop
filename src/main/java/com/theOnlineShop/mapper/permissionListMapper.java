package com.theOnlineShop.mapper;

import com.theOnlineShop.domain.permissionEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface permissionListMapper {
    public List<permissionEntity> selectListByRoleName(permissionEntity permission);

}
