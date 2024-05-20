package it.polimi.codexnaturalis.network.communicationInterfaces;

import it.polimi.codexnaturalis.network.lobby.LobbyInfo;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface VirtualServer extends Remote {
    String connect(VirtualView client) throws RemoteException, InterruptedException;
    //return false if the setting of nickname fails
    boolean setNickname(String userID, String nickname) throws RemoteException;
    ArrayList<LobbyInfo> getAvailableLobby() throws RemoteException;
    boolean joinLobby(String playerNickname, String lobbyName) throws RemoteException;
    void leaveLobby(String playerNickname, String lobbyName) throws RemoteException;
    boolean createLobby(String playerNickname, String lobbyName) throws RemoteException;
    void setPlayerReady(String playerNickname, String lobbyName) throws RemoteException;
}
