package it.polimi.codexnaturalis.view;

import it.polimi.codexnaturalis.network.lobby.LobbyInfo;

import java.rmi.RemoteException;
import java.util.ArrayList;

public interface VirtualNetworkCommand {
    void selectNickname(String nickname) throws RemoteException;
    ArrayList<LobbyInfo> getLobbies() throws RemoteException;
    void joinLobby(String selection) throws RemoteException;
    void createLobby(String lobbyName) throws RemoteException;
    void setReady() throws RemoteException;
    void leaveLobby() throws RemoteException;
}
