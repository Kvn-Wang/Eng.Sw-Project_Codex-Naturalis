package it.polimi.codexnaturalis.model.player;

import it.polimi.codexnaturalis.model.enumeration.ColorType;
import it.polimi.codexnaturalis.model.mission.Mission;
import it.polimi.codexnaturalis.model.shop.card.Card;
import it.polimi.codexnaturalis.utils.PersonalizedException;

public interface PlayerInterface {
    int executePersonalMission();
    void addHandCard(Card drawnCard);
    void placeCard(int x, int y, int numCard, boolean isCardBack) throws PersonalizedException.InvalidPlacementException;
    void addMissionScore(int Value);
    boolean isPlayerAlive();
    void setStatus(boolean status);//setta il player alive o dead
    void setPersonalMissions(Mission mission1,Mission mission2);
    void setPersonalMissionFinal(int selection);
    void switchPlayerView(Player target);
    int getPersonalScore();
    int getPersonalMissionTotalScore();
    ColorType getPawnColor();
    void setNickname(String nickname);
    void setPawnColor(ColorType color);
}
