package it.polimi.codexnaturalis.model.player;

import it.polimi.codexnaturalis.model.mission.Mission;
import it.polimi.codexnaturalis.model.shop.card.Card;

public interface PlayerInterface {
    int executePersonalMission(Card[][] mapArray);
    void drawCard(Card drawnCard);
    void placeCard(int x, int y, int numCard);
    void addScore(int Value);
    boolean isPlayerAlive();
    void setPersonalMissionChoice(Mission selectedPersonalMission);
    void setPersonalMissionFinal();
    void switchPlayerView();
    int getPersonalScore();
    int getPersonalMissionTotalScore();
    String getPawnColor();
    void setNickname(String nickname);
    void setPawnColor(String pawnColor);
}
