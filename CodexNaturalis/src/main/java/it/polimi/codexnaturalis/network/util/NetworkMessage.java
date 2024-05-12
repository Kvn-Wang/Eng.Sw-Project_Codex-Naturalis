package it.polimi.codexnaturalis.network.util;

import it.polimi.codexnaturalis.model.enumeration.MessageType;

public class NetworkMessage {
    String nickname;
    MessageType messageType;
    String args;

    //useful for TCP communication
    public NetworkMessage(MessageType messageType, String args) {
        this.messageType = messageType;
    }

    public NetworkMessage(String nickname, MessageType messageType) {
        this.nickname = nickname;
        this.messageType = messageType;
    }

    public NetworkMessage(String nickname, MessageType messageType, String args) {
        this.nickname = nickname;
        this.messageType = messageType;
        this.args = args;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public String getArgs() {
        return args;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }
}
