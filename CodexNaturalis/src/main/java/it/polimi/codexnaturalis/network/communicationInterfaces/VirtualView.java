package it.polimi.codexnaturalis.network.communicationInterfaces;

import it.polimi.codexnaturalis.controller.GameController;
import it.polimi.codexnaturalis.network.util.NetworkMessage;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface VirtualView extends Remote {
    public void showMessage(NetworkMessage message) throws RemoteException;
    public void reportError(String details) throws RemoteException;
    public void connectToGame(GameController gameController) throws RemoteException;
}

