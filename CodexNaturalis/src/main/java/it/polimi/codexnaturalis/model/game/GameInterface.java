package it.polimi.codexnaturalis.model.game;

public interface GameInterface {
    void initializeGame();
    void disconnectPlayer(String nickname);//vedi issues
    void reconnectPlayer(String nickname);
    void playerDraw(String nickname, int Numcard, String type);// vedi issues
    void playerPersonalMissionSelect(String nickname, int numMission);
    void playerPlayCard(String nickname, int x, int y, int numCard);
    void typeMessage(String receiver, String sender, String msg);// vedi issues
    void switchPlayer(String nickname);
    void endGame();
}