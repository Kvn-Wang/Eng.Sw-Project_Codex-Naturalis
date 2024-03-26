package it.polimi.codexnaturalis.model;

import it.polimi.codexnaturalis.model.player.Player;

public interface GameInterface {
    void initializeGame();
    void disconnectPlayer(String nickname);//vedi issues
    void reconnectPlayer(String nickname);
    void playerDraw(String nickname, int Numcard, String type);// vedi issues
    void playerPersonalMissionSelect(String nickname, int numMission);
    void playerPlayCard(String nickname, int numCard);
    void typeMessage(Player reciver, Player sender, String msg);// vedi issues
    void switchPlayer(String nickname);
    void endGame();
}