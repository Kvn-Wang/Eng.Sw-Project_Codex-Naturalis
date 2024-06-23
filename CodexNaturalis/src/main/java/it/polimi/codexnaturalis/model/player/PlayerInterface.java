package it.polimi.codexnaturalis.model.player;

import it.polimi.codexnaturalis.model.enumeration.ColorType;
import it.polimi.codexnaturalis.model.mission.Mission;
import it.polimi.codexnaturalis.model.shop.card.Card;
import it.polimi.codexnaturalis.utils.PersonalizedException;

public interface PlayerInterface {
    int executePersonalMission();
    void placeCard(int x, int y, Card playedCard) throws PersonalizedException.InvalidPlaceCardRequirementException, PersonalizedException.InvalidPlacementException;
    void addMissionScore(int Value);
    void setPersonalMissionFinal(Mission personalMission);
    int getPersonalScore();
    int getPersonalMissionTotalScore();
    void setNickname(String nickname);
}
