package com.theOnlineShop.websocket.webSocketCommand.enums;

public enum constantCommand {

    Update("00","update"),
    Camera("01","camera");

    private String order;
    private String name;

    constantCommand(String order, String name) {
        this.order = order;
        this.name = name;
    }

    public static String getOrder(String name){
        for (constantCommand cc : constantCommand.values()) {
            if(cc.getName().equals(name)){
                return cc.order;
            }
        }
        return null;
    }

    public static String getName(String order){
        for (constantCommand cc : constantCommand.values()) {
            if(cc.getOrder().equals(order)){
                return cc.name;
            }
        }
        return null;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
