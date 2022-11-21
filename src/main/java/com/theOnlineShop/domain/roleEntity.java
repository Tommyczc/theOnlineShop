package com.theOnlineShop.domain;

public class roleEntity {
    private String userName;
    private String roleName;

    public roleEntity(){}
    public roleEntity(String userName, String roleName){
        this.roleName=roleName;
        this.userName=userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
