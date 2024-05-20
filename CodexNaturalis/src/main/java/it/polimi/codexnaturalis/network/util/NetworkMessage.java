package it.polimi.codexnaturalis.network.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class NetworkMessage implements Serializable {
    String nickname;
    MessageType messageType;
    ArrayList<String> args = new ArrayList<>();;

    //useful for TCP communication
    public NetworkMessage(MessageType messageType, String ... newArgs) {
        this.nickname = null;
        this.messageType = messageType;
        // Aggiungi tutti i nuovi argomenti alla lista
        Collections.addAll(args, newArgs);
    }

    public NetworkMessage(MessageType messageType) {
        this.nickname = null;
        this.messageType = messageType;
        this.args = null;
    }

    public NetworkMessage(String nickname, MessageType messageType) {
        this.nickname = nickname;
        this.messageType = messageType;
        this.args = null;
    }

    public NetworkMessage(String nickname, MessageType messageType, String ... newArgs) {
        this.nickname = nickname;
        this.messageType = messageType;
        System.out.println(newArgs[0]);
        System.out.println(args);

        // Aggiungi tutti i nuovi argomenti alla lista
        Collections.addAll(args, newArgs);
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

    public ArrayList<String> getArgs() {
        return args;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }
}
