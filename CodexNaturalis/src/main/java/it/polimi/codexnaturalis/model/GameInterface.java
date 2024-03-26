package it.polimi.codexnaturalis.model;

import it.polimi.codexnaturalis.model.player.Player;

public interface GameInterface {
    void initializeGame();
    void disconnectPlayer(Player nickname);//vedi issues
    void reconnectPlayer(Player nickname);
    void playerDraw(Player nickname, int Numcard, String type);// vedi issues
    void playerPersonalMissionSelect(Player nickname, int numMission);
    void playerPlayCard(Player nickname, int numCard);
    void typeMessage(Player reciver, Player sender, String msg);// vedi issues
    void switchPlayer(Player nickname);
    void endGame();
}