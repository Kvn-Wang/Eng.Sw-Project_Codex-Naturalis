package it.polimi.codexnaturalis.model.player;

import it.polimi.codexnaturalis.model.shop.card.Card;

public interface PlayerInterface {
    int executePersonalMission(Card[][] mapArray);
    void drawCard(int numCard);
    void placeCard(int numCard);
    void addScore(int Value);
    boolean isPlayerAlive();
    void setPersonalMissionChoice();
    void setPersonalMissionFinal();
    void switchPlayerView();
}
