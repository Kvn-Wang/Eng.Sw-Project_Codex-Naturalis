package it.polimi.codexnaturalis.controller;

import it.polimi.codexnaturalis.utils.PersonalizedException;

public interface GameController {
    void initializeGame();
//void setPlayerColor(String nickname, String color);
    void disconnectPlayer(String nickname);//vedi issues
    void reconnectPlayer(String nickname);
    void playerDraw(String nickname, int Numcard, String type);// vedi issues
    void playerPersonalMissionSelect(String nickname, int numMission);
    void playerPlayCard(String nickname, int x, int y, int numCard, boolean isCardBack) throws PersonalizedException.InvalidPlacementException;
    void playerPlayStarterCard(String nickname, boolean isCardBack) throws PersonalizedException.InvalidPlacementException;
    void typeMessage(String receiver, String sender, String msg);// vedi issues
    void switchPlayer(String reqPlayer, String target);
    void endGame();
}