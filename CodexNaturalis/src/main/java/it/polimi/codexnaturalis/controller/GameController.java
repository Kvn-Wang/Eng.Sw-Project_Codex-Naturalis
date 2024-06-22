package it.polimi.codexnaturalis.controller;

import it.polimi.codexnaturalis.model.enumeration.ColorType;
import it.polimi.codexnaturalis.model.enumeration.ShopType;
import it.polimi.codexnaturalis.model.mission.Mission;
import it.polimi.codexnaturalis.model.shop.card.Card;
import it.polimi.codexnaturalis.model.shop.card.StarterCard;
import it.polimi.codexnaturalis.utils.PersonalizedException;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GameController extends Remote {
    void initializeGame() throws RemoteException;

    /* ---- GAME PHASE: SETUP ---- */
    void playStarterCard(String playerNick, StarterCard starterCard) throws RemoteException;
    void playerPersonalMissionSelect(String nickname, Mission mission) throws RemoteException;

    /* ---- GAME PHASE: Play ---- */
    void disconnectPlayer(String nickname) throws RemoteException;//vedi issues
    void reconnectPlayer(String nickname) throws RemoteException;
    void playerDraw(String nickname, int numcard, ShopType type) throws RemoteException;// vedi issues
    void playerPlayCard(String nickname, int x, int y, Card playedCard) throws RemoteException;
    void typeMessage(String receiver, String sender, String msg) throws RemoteException;// vedi issues
    void switchPlayer(String reqPlayer, String target) throws RemoteException;
}