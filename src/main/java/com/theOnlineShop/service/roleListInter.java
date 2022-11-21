package com.theOnlineShop.service;

import com.theOnlineShop.domain.roleEntity;

import java.util.List;

public interface roleListInter {
    //返回的结果:
    //1:成功插入
    //2:该权限角色已超过额定数量
    //0:数据插入失败
    public int insertRole(roleEntity role);
    public boolean updateRole(roleEntity role);
    public List<roleEntity> findRole(roleEntity role);
    public List<roleEntity> findAllRole();

}
