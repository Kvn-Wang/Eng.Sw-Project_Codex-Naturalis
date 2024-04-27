package it.polimi.codexnaturalis.network;

import it.polimi.codexnaturalis.network.rmi.VirtualView;
import javafx.scene.paint.Color;

import java.rmi.RemoteException;

public class PlayerInfo implements VirtualView {
    public Color colorChosen;
    public boolean isPlayerReady;

    public PlayerInfo() {
        this.colorChosen = null;
        this.isPlayerReady = false;
    }

    @Override
    public void showValue(Integer number) throws RemoteException {

    }

    @Override
    public void reportError(String details) throws RemoteException {

    }

    @Override
    public boolean askNickname() throws RemoteException {
        return false;
    }
}
