package it.polimi.codexnaturalis.network;

import it.polimi.codexnaturalis.network.rmi.VirtualView;
import javafx.scene.paint.Color;

import java.rmi.RemoteException;

public class PlayerInfo implements VirtualView {
    private String nickname;
    private Color colorChosen;
    private boolean isPlayerReady;

    public PlayerInfo() {
        this.nickname = null;
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

    //TODO: cambiare l'argomento in NetworkMessage
    @Override
    public void showValue(String message) throws RemoteException {

    }

    @Override
    public void reportError(String details) throws RemoteException {

    }

    @Override
    public void askNickname() throws RemoteException {

    }
}
