package com.theOnlineShop.service.serviceImpl;

import com.theOnlineShop.domain.roleEntity;
import com.theOnlineShop.mapper.roleListMapper;
import com.theOnlineShop.service.roleListInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;



@Service
public class roleListImpl implements roleListInter {

    @Value("${personal.role.adminNum}")
    private int adminNum;
    @Value("${personal.role.superAdminNum}")
    private int superAdminNum;
    @Value("${personal.role.userNum}")
    private int userNum;

    @Autowired
    private roleListMapper roleMapper;

    @Override
    public int insertRole(roleEntity role) {
        List<roleEntity> roleList=roleMapper.selectListByUserName(role);
        if(role.getRoleName().equals("admin")){
            if(adminNum!=-1){
                if(roleList.size()<=adminNum){
                    return roleMapper.insertRole(role);
                }
                else{return 2;}
            }
            else{
                return roleMapper.insertRole(role);
            }
        }

        else if(role.getRoleName().equals("superAdmin")){
            if(superAdminNum!=-1){
                if(roleList.size()<=superAdminNum){
                    return roleMapper.insertRole(role);
                }
                else{return 2;}
            }
            else{
                return roleMapper.insertRole(role);
            }
        }

        else if(role.getRoleName().equals("user")){
            if(userNum!=-1){
                if(roleList.size()<=userNum){
                    return roleMapper.insertRole(role);
                }
                else{return 2;}
            }
            else{
                return roleMapper.insertRole(role);
            }
        }

        return 0;
    }

    @Override
    public boolean updateRole(roleEntity role) {
        int i=roleMapper.updateRole(role);
        if(i==1){return true;}
        return false;
    }

    @Override
    public List<roleEntity> findRole(roleEntity role) {
        return roleMapper.selectListByUserName(role);
    }

    @Override
    public List<roleEntity> findAllRole() {
        return roleMapper.selectListAll();
    }
}
