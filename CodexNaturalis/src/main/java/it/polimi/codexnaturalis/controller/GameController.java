package it.polimi.codexnaturalis.controller;

import it.polimi.codexnaturalis.model.enumeration.ColorType;
import it.polimi.codexnaturalis.utils.PersonalizedException;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GameController extends Remote {
    void initializeGame() throws RemoteException;;
    //void setPlayerColor(String nickname, String color);
    void disconnectPlayer(String nickname) throws RemoteException;//vedi issues
    void reconnectPlayer(String nickname) throws RemoteException;
    void playerDraw(String nickname, int Numcard, String type) throws PersonalizedException.InvalidRequestTypeOfNetworkMessage, RemoteException;// vedi issues
    void playerPersonalMissionSelect(String nickname, int numMission) throws RemoteException;
    void playerPlayCard(String nickname, int x, int y, int numCard, boolean isCardBack) throws PersonalizedException.InvalidPlacementException, PersonalizedException.InvalidPlaceCardRequirementException, RemoteException;
    void typeMessage(String receiver, String sender, String msg) throws RemoteException;// vedi issues
    void switchPlayer(String reqPlayer, String target) throws RemoteException;
    void endGame() throws RemoteException;
}