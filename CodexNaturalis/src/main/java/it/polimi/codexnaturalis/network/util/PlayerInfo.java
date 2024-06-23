package it.polimi.codexnaturalis.network.util;

import it.polimi.codexnaturalis.model.enumeration.ColorType;
import it.polimi.codexnaturalis.network.communicationInterfaces.VirtualView;
import it.polimi.codexnaturalis.network.util.networkMessage.NetworkMessage;

import java.io.Serializable;
import java.rmi.RemoteException;


public class PlayerInfo implements Serializable {
    private String nickname;
    private ColorType colorChosen;
    private boolean isPlayerReady;
    private transient VirtualView clientHandler;

    public PlayerInfo(VirtualView clientHandler, String nickname) {
        this.clientHandler = clientHandler;
        this.nickname = nickname;
        this.colorChosen = null;
        this.isPlayerReady = false;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setColorChosen(ColorType colorChosen) {
        this.colorChosen = colorChosen;
    }

    public ColorType getColorChosen() {
        return colorChosen;
    }

    public boolean isPlayerReady() {
        return isPlayerReady;
    }

    public void setPlayerReady(boolean playerReady) {
        isPlayerReady = playerReady;
    }

    public void notifyPlayer(NetworkMessage message) throws RemoteException {
        clientHandler.showMessage(message);
    }

    public VirtualView getClientHandler() {
        return clientHandler;
    }
}
