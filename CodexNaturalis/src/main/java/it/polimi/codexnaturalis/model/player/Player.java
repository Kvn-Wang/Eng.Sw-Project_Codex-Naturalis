package it.polimi.codexnaturalis.model.player;

import it.polimi.codexnaturalis.model.enumeration.ColorType;
import it.polimi.codexnaturalis.model.enumeration.ResourceType;
import it.polimi.codexnaturalis.model.mission.Mission;
import it.polimi.codexnaturalis.model.shop.card.Card;
import it.polimi.codexnaturalis.network.util.networkMessage.MessageType;
import it.polimi.codexnaturalis.network.util.networkMessage.NetworkMessage;
import it.polimi.codexnaturalis.utils.PersonalizedException;
import it.polimi.codexnaturalis.utils.observer.Observable;
import it.polimi.codexnaturalis.utils.observer.Observer;

/**
 * The type Player.
 */
public class Player extends Observable implements PlayerInterface {
    private String nickname;
    private int personalScoreBoardScore;
    private int personalMissionTotalScore;
    private ColorType pawnColor;
    private Mission selectedPersonalMission;
    private PlayerScoreResource scoreResource;
    private GamePlayerMap gameMap;

    /**
     * Instantiates a new Player.
     *
     * @param nick  the nick
     * @param color the color
     */
    public Player(String nick, ColorType color){
        nickname = nick;
        pawnColor = color;
        personalScoreBoardScore = 0;
        personalMissionTotalScore = 0;
        selectedPersonalMission = null;
        scoreResource = new PlayerScoreResource();
        gameMap = new GamePlayerMap(scoreResource);
    }

    /**
     * Instantiates a new Player.
     *
     * @param nick     the nick
     * @param color    the color
     * @param observer the observer
     */
    public Player(String nick, ColorType color, Observer observer){
        nickname = nick;
        pawnColor = color;
        personalScoreBoardScore = 0;
        personalMissionTotalScore = 0;
        selectedPersonalMission = null;
        scoreResource = new PlayerScoreResource();
        gameMap = new GamePlayerMap(scoreResource);
        addObserver(observer);
    }

    /**
     * Gets nickname.
     *
     * @return the nickname
     */
    public String getNickname() {
        return nickname;
    }

    @Override
    public int executePersonalMission() {
        return selectedPersonalMission.ruleAlgorithmCheck(this);
    }

    @Override
    public void placeCard(int x, int y, Card playedCard) throws PersonalizedException.InvalidPlaceCardRequirementException, PersonalizedException.InvalidPlacementException {
        int placeResult;

        try {
            placeResult = gameMap.placeCard(x, y, playedCard);
            personalScoreBoardScore+=placeResult;
        } catch (PersonalizedException.InvalidPlacementException |
                 PersonalizedException.InvalidPlaceCardRequirementException e) {
            throw e;
        }
    }

    @Override
    public void addMissionScore(int value) {
        personalMissionTotalScore = personalMissionTotalScore + value;
        personalScoreBoardScore = personalScoreBoardScore + value;
    }

    @Override
    public void setPersonalMissionFinal(Mission personalMission) {
        selectedPersonalMission = personalMission;
    }

    @Override
    public int getPersonalMissionTotalScore() {
        return personalMissionTotalScore;
    }

    @Override
    public int getPersonalScore() {
        return personalScoreBoardScore;
    }

    @Override
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * Gets score resource.
     *
     * @return the score resource
     */
    public PlayerScoreResource getScoreResource() {
        return scoreResource;
    }

    /**
     * Gets game map.
     *
     * @return the game map
     */
    public GamePlayerMap getGameMap() {
        return gameMap;
    }

    /**
     * Gets personal score board score.
     *
     * @return the personal score board score
     */
    public int getPersonalScoreBoardScore() {
        return personalScoreBoardScore;
    }

    /**
     * Add score.
     *
     * @param value the value
     */
    public void addScore(int value){
        personalScoreBoardScore = personalScoreBoardScore+value;
    }
}