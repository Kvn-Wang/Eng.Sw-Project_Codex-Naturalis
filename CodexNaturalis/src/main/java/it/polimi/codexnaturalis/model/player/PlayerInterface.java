package it.polimi.codexnaturalis.model.player;

import it.polimi.codexnaturalis.model.mission.Mission;
import it.polimi.codexnaturalis.model.shop.card.Card;

public interface PlayerInterface {
    int executePersonalMission();
    void addHandCard(Card drawnCard);
    void placeCard(int x, int y, int numCard);
    void addMissionScore(int Value);
    boolean isPlayerAlive();
    void setStatus(boolean status);//setta il player alive o dead
    void setPersonalMissions(Mission mission1,Mission mission2);
    void setPersonalMissionChoice(Mission selectedPersonalMission);
    void setPersonalMissionFinal(int selection);
    void switchPlayerView();
    int getPersonalScore();
    int getPersonalMissionTotalScore();
    String getPawnColor();
    void setNickname(String nickname);
    void setPawnColor(String pawnColor);
}
