package com.theOnlineShop.domain;

public class httpReponseEntity {
    private String messageType;
    private String messageBody;

    public httpReponseEntity(String type,String body){
        this.messageType=type;
        this.messageBody=body;
    }
    public httpReponseEntity(){}

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }
}
