package it.polimi.codexnaturalis.network.rmi;

import it.polimi.codexnaturalis.controller.GameController;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface VirtualServer extends Remote {
    public void connect(VirtualView client) throws RemoteException;
    public String getPersonalID() throws RemoteException;
    //return false if the setting of nickname fails
    public boolean setNickname(String userID, String nickname) throws RemoteException;
    public String getAvailableLobby(String nickname) throws RemoteException;
}
