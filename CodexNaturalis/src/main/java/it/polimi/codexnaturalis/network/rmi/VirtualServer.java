package it.polimi.codexnaturalis.network.rmi;

import it.polimi.codexnaturalis.controller.GameController;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface VirtualServer extends Remote {
    public String connect(VirtualView client) throws RemoteException, InterruptedException;
    //return false if the setting of nickname fails
    public boolean setNickname(String userID, String nickname) throws RemoteException;
    public String getAvailableLobby(String nickname) throws RemoteException;
    public boolean joinLobby(String playerNickname, String lobbyName) throws RemoteException;
    public void leaveLobby(String playerNickname, String lobbyName) throws RemoteException;
    public boolean createLobby(String playerNickname, String lobbyName) throws RemoteException;
    public void setPlayerReady(String playerNickname, String lobbyName) throws RemoteException;
}
