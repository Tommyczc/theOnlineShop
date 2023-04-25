package com.theOnlineShop.websocket;

import java.util.ArrayList;

public class NodeInstance {
    public String address;
    public ArrayList<chipInstance> chipInstanceList;
    public String registerName;

    public String registerTime;

    public NodeInstance(String address,String registerTime) {
        this.address = address;
        this.chipInstanceList = new ArrayList<chipInstance>();
        this.registerTime=registerTime;
    }

    public String getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(String registerTime) {
        this.registerTime = registerTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ArrayList<chipInstance> getChipInstanceList() {
        return chipInstanceList;
    }

    public void setChipInstanceList(ArrayList<chipInstance> chipInstanceList) {
        this.chipInstanceList = chipInstanceList;
    }

    public String getRegisterName() {
        return registerName;
    }

    public void setRegisterName(String registerName) {
        this.registerName = registerName;
    }
}
