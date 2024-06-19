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

public class Player extends Observable implements PlayerInterface {
    private String nickname;
    private int personalScoreBoardScore;
    private int personalMissionTotalScore;
    private ColorType pawnColor;
    private boolean alive;
    private Mission selectedPersonalMission;
    private Player playerView;
    private PlayerScoreResource scoreResource;
    private GamePlayerMap gameMap;
    private Hand hand;
    private String pawnImg;

    public Player(String nick, ColorType color){
        nickname = nick;
        pawnColor = color;
        personalScoreBoardScore = 0;
        personalMissionTotalScore = 0;
        selectedPersonalMission = null;
        playerView = this;
        scoreResource = new PlayerScoreResource();
        gameMap = new GamePlayerMap(scoreResource);
        hand = new Hand();
        pawnImg = null;//TODO:mettere case con inserimento immagine
        alive = true;
    }

    public void addHandCard(Card drawnCard) {
        try {
            hand.addCard(drawnCard);
            //notifyObserverSingle(new NetworkMessage(nickname, MessageType.CORRECT_DRAW_CARD, this.argsGenerator(hand)));
        } catch (PersonalizedException.InvalidAddCardException e) {
            throw new RuntimeException(e);
        }
    }

    public String getNickname() {
        return nickname;
    }

    public Hand getHand() {
        return hand;
    }

    @Override
    public int executePersonalMission() {
        return selectedPersonalMission.ruleAlgorithmCheck(this);
    }

    @Override
    public void placeCard(int x, int y, Card playedCard) throws PersonalizedException.InvalidPlaceCardRequirementException, PersonalizedException.InvalidPlacementException {
        //Card playedCard;
        int placeResult;

        /*try {
            playedCard = hand.popCard(numCard);
        } catch (PersonalizedException.InvalidNumPopCardException e) {
            throw new RuntimeException(e);
        } catch (PersonalizedException.InvalidPopCardException e) {
            throw new RuntimeException(e);
        }*/

        try {
            placeResult = gameMap.placeCard(x, y, playedCard);
            personalScoreBoardScore+=placeResult;
        } catch (PersonalizedException.InvalidPlacementException |
                 PersonalizedException.InvalidPlaceCardRequirementException e) {
            //ripiazza la carta nella mano
            /*try {
                hand.addCard(playedCard);
            } catch (PersonalizedException.InvalidAddCardException ex) {
                throw new RuntimeException(ex);
            }*/
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
    public void switchPlayerView(Player target) {
        playerView=target;
        try {
            notifyObserverSingle(new NetworkMessage(nickname, MessageType.SWITCH_PLAYER_VIEW, argsGenerator(playerView.getGameMap())));
        } catch (PersonalizedException.InvalidRequestTypeOfNetworkMessage e) {
            throw new RuntimeException(e);
        }
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
    public ColorType getPawnColor() {
        return pawnColor;
    }

    @Override
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    @Override
    public void setStatus(boolean status){
        alive = status;
        try {
            notifyObserverSingle(new NetworkMessage(nickname, MessageType.STATUS_PLAYER_CHANGE, Boolean.toString(status)));
        } catch (PersonalizedException.InvalidRequestTypeOfNetworkMessage e) {
            throw new RuntimeException(e);
        }
    }

    public PlayerScoreResource getScoreResource() {
        return scoreResource;
    }

    public GamePlayerMap getGameMap() {
        return gameMap;
    }

    public void setGameMap(GamePlayerMap gameMap) {
        this.gameMap = gameMap;
    }

    public Player(String nick, ColorType color, Observer observer){
        nickname = nick;
        pawnColor = color;
        personalScoreBoardScore = 0;
        personalMissionTotalScore = 0;
        selectedPersonalMission = null;
        playerView = this;
        scoreResource = new PlayerScoreResource();
        gameMap = new GamePlayerMap(scoreResource);
        hand = new Hand();
        pawnImg = null;//TODO:mettere case con inserimento immagine
        alive = true;
        addObserver(observer);
    }

    public int getPersonalScoreBoardScore() {
        return personalScoreBoardScore;
    }

    public String getPawnImg() {
        return pawnImg;
    }

    public void setPawnImg(String pawnImg) {
        this.pawnImg = pawnImg;
    }

    protected void updateReducedPlayerScore(ResourceType type){
        scoreResource.substractScore(type);
    }

    protected void updateAddPlayerScore(ResourceType type){
        scoreResource.addScore(type);
    }

    public void addScore(int value){
        personalScoreBoardScore = personalScoreBoardScore+value;
    }
}