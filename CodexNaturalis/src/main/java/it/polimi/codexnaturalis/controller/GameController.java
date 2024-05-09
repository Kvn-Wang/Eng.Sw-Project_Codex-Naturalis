package it.polimi.codexnaturalis.controller;

import it.polimi.codexnaturalis.model.enumeration.ColorType;
import it.polimi.codexnaturalis.utils.PersonalizedException;

import java.io.Serializable;

public interface GameController extends Serializable {
    void initializeGame();
//void setPlayerColor(String nickname, String color);
    void disconnectPlayer(String nickname);//vedi issues
    void reconnectPlayer(String nickname);
    void playerDraw(String nickname, int Numcard, String type) throws PersonalizedException.InvalidRequestTypeOfNetworkMessage;// vedi issues
    void playerPersonalMissionSelect(String nickname, int numMission);
    void playerPlayCard(String nickname, int x, int y, int numCard, boolean isCardBack) throws PersonalizedException.InvalidPlacementException, PersonalizedException.InvalidPlaceCardRequirementException;
    void typeMessage(String receiver, String sender, String msg);// vedi issues
    void switchPlayer(String reqPlayer, String target);
    void endGame();
}