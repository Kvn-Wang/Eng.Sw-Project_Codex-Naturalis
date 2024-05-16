package it.polimi.codexnaturalis.view;

import java.rmi.RemoteException;

public interface VirtualNetworkCommand {
    void selectNickname(String nickname) throws RemoteException;
    void joinLobby(String selection) throws RemoteException;
    void createLobby(String lobbyName) throws RemoteException;
    void setReady() throws RemoteException;
    void leaveLobby() throws RemoteException;
}
