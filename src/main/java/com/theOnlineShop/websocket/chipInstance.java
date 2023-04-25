package com.theOnlineShop.websocket;

public class chipInstance {
    //设备名称
    private String deviceName;
    //子设备ip地址，也可作为子设备名称
    private String siteAddress;
    //上次数据更新的时间
    private String updateDate;

    public chipInstance(String deviceName, String siteAddress) {
        this.deviceName = deviceName;
        this.siteAddress = siteAddress;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getSiteAddress() {
        return siteAddress;
    }

    public void setSiteAddress(String siteAddress) {
        this.siteAddress = siteAddress;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }
}
