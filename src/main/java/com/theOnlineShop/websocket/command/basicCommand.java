package com.theOnlineShop.websocket.command;

public enum basicCommand {

    Time("Time","00");
    private String commandName;
    private String order;

    basicCommand(String commandName, String order) {
        this.commandName = commandName;
        this.order = order;
    }

    public String getCommandName() {
        return commandName;
    }

    public void setCommandName(String commandName) {
        this.commandName = commandName;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
