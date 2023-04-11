package com.theOnlineShop.websocket;

import java.util.ArrayList;

public class NodeInstance {
    public String address;
    public ArrayList<chipInstance> chipInstanceList;

    public NodeInstance(String address) {
        this.address = address;
        this.chipInstanceList = new ArrayList<chipInstance>();
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
}
