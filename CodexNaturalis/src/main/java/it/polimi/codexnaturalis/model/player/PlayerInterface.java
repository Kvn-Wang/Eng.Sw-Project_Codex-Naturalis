package it.polimi.codexnaturalis.model.player;

import it.polimi.codexnaturalis.model.enumeration.ColorType;
import it.polimi.codexnaturalis.model.mission.Mission;
import it.polimi.codexnaturalis.model.shop.card.Card;
import it.polimi.codexnaturalis.utils.PersonalizedException;

/**
 * The interface Player interface.
 */
public interface PlayerInterface {
    /**
     * Execute personal mission int.
     *
     * @return the int
     */
    int executePersonalMission();

    /**
     * Place card.
     *
     * @param x          the x
     * @param y          the y
     * @param playedCard the played card
     * @throws InvalidPlaceCardRequirementException the invalid place card requirement exception
     * @throws InvalidPlacementException            the invalid placement exception
     */
    void placeCard(int x, int y, Card playedCard) throws PersonalizedException.InvalidPlaceCardRequirementException, PersonalizedException.InvalidPlacementException;

    /**
     * Add mission score.
     *
     * @param Value the value
     */
    void addMissionScore(int Value);

    /**
     * Sets personal mission final.
     *
     * @param personalMission the personal mission
     */
    void setPersonalMissionFinal(Mission personalMission);

    /**
     * Gets personal score.
     *
     * @return the personal score
     */
    int getPersonalScore();

    /**
     * Gets personal mission total score.
     *
     * @return the personal mission total score
     */
    int getPersonalMissionTotalScore();

    /**
     * Sets nickname.
     *
     * @param nickname the nickname
     */
    void setNickname(String nickname);
}
