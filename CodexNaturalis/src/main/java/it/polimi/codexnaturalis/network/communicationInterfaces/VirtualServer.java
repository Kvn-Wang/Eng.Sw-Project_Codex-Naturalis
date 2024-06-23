package it.polimi.codexnaturalis.network.communicationInterfaces;

import it.polimi.codexnaturalis.model.enumeration.ColorType;
import it.polimi.codexnaturalis.network.lobby.LobbyInfo;
import it.polimi.codexnaturalis.network.util.PlayerInfo;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface VirtualServer extends Remote {
    void connectRMI(VirtualView client, String UUID) throws RemoteException, InterruptedException;
    //return false if the setting of nickname fails
    boolean setNickname(String UUID, String nickname) throws RemoteException;
    ArrayList<LobbyInfo> getAvailableLobby() throws RemoteException;

    /**
     *
     * @param playerNickname
     * @param lobbyName
     * @return ArrayList<PlayerInfo> != null means that the join was successful
     * @throws RemoteException
     */
    ArrayList<PlayerInfo> joinLobby(String playerNickname, String lobbyName) throws RemoteException;
    void leaveLobby(String playerNickname) throws RemoteException;
    boolean createLobby(String playerNickname, String lobbyName) throws RemoteException;
    void setPlayerColor(String nickname, ColorType colorChosen) throws RemoteException;
    void setPlayerReady(String playerNickname) throws RemoteException;
}
