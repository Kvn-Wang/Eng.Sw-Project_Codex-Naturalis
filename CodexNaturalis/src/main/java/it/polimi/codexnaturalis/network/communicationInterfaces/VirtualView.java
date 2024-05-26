package it.polimi.codexnaturalis.network.communicationInterfaces;

import it.polimi.codexnaturalis.controller.GameController;
import it.polimi.codexnaturalis.network.util.NetworkMessage;
import it.polimi.codexnaturalis.network.util.PlayerInfo;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface VirtualView extends Remote {
    public void showMessage(NetworkMessage message) throws RemoteException;
    public void connectToGame(GameController gameController, ArrayList<PlayerInfo> listOtherPlayer) throws RemoteException;
}

