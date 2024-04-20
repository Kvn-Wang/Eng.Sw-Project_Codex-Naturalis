package it.polimi.codexnaturalis.network;

import it.polimi.codexnaturalis.model.enumeration.MessageType;

public abstract class NetworkMessage {
    String nickname;
    MessageType messageType;

    public NetworkMessage(String nickname, MessageType messageType) {
        this.nickname = nickname;
        this.messageType = messageType;
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

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }
}
