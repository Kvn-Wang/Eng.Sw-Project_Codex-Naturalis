package it.polimi.codexnaturalis.network.rmi;

import it.polimi.codexnaturalis.controller.GameController;
import it.polimi.codexnaturalis.network.Lobby.LobbyInfo;
import it.polimi.codexnaturalis.network.Lobby.LobbyThread;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface VirtualServer extends Remote {
    public String connect(VirtualView client) throws RemoteException, InterruptedException;
    //return false if the setting of nickname fails
    public boolean setNickname(String userID, String nickname) throws RemoteException;
    public ArrayList<LobbyInfo> getAvailableLobby(String nickname) throws RemoteException;
    public boolean joinLobby(String playerNickname, String lobbyName) throws RemoteException;
    public void leaveLobby(String playerNickname, String lobbyName) throws RemoteException;
    public boolean createLobby(String playerNickname, String lobbyName) throws RemoteException;
    public void setPlayerReady(String playerNickname, String lobbyName) throws RemoteException;
}
