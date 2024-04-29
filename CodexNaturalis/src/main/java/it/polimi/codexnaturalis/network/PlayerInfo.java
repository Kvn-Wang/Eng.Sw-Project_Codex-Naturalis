package it.polimi.codexnaturalis.network;

import it.polimi.codexnaturalis.network.rmi.VirtualView;
import javafx.scene.paint.Color;

import java.rmi.RemoteException;


public class PlayerInfo  {
    private String nickname;
    private Color colorChosen;
    private boolean isPlayerReady;
    private VirtualView clientHandler;

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

    public Color getColorChosen() {
        return colorChosen;
    }

    public void setColorChosen(Color colorChosen) {
        this.colorChosen = colorChosen;
    }

    public boolean isPlayerReady() {
        return isPlayerReady;
    }

    public void setPlayerReady(boolean playerReady) {
        isPlayerReady = playerReady;
    }

    public void notifyPlayer(String message) throws RemoteException {
        clientHandler.showValue(message);
    }

    public VirtualView getClientHandler() {
        return clientHandler;
    }
}
