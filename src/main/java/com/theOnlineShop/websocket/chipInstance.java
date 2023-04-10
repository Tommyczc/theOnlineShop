package com.theOnlineShop.websocket;

public class chipInstance {
    //节点名称
    private String nodeName;
    //子设备ip地址，也可作为子设备名称
    private String siteAddress;

    public chipInstance(String nodeName, String siteAddress) {
        this.nodeName = nodeName;
        this.siteAddress = siteAddress;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getSiteAddress() {
        return siteAddress;
    }

    public void setSiteAddress(String siteAddress) {
        this.siteAddress = siteAddress;
    }
}
