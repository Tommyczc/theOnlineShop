package com.theOnlineShop.websocket;

import java.util.ArrayList;

public class NodeInstance {
    public String address;
    public ArrayList<chipInstance> instanceList;

    public NodeInstance(String address, ArrayList<chipInstance> instanceList) {
        this.address = address;
        this.instanceList = instanceList;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ArrayList<chipInstance> getInstanceList() {
        return instanceList;
    }

    public void setInstanceList(ArrayList<chipInstance> instanceList) {
        this.instanceList = instanceList;
    }
}
