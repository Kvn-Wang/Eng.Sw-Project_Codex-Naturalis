package it.polimi.codexnaturalis.model.player;

import it.polimi.codexnaturalis.model.enumeration.ColorType;
import it.polimi.codexnaturalis.model.enumeration.ResourceType;
import it.polimi.codexnaturalis.model.mission.Mission;
import it.polimi.codexnaturalis.model.shop.card.Card;
import it.polimi.codexnaturalis.network.util.MessageType;
import it.polimi.codexnaturalis.network.util.NetworkMessage;
import it.polimi.codexnaturalis.utils.PersonalizedException;
import it.polimi.codexnaturalis.utils.observer.Observable;
import it.polimi.codexnaturalis.utils.observer.Observer;

public class Player extends Observable implements PlayerInterface {
    private String nickname;
    private int personalScoreBoardScore;
    private int personalMissionTotalScore;
    private ColorType pawnColor;
    private boolean alive;
    private Mission personalMission1;
    private Mission personalMission2;
    private Mission selectedPersonalMission;
    private Player playerView;
    private PlayerScoreResource scoreResource;
    private GamePlayerMap gameMap;
    private Hand hand;
    private String pawnImg;

//    public void inizializeGamePlayerMap(boolean isBackStarterCard) {
//        starterCard.setIsBack(isBackStarterCard);
//        gameMap = new GamePlayerMap(scoreResource, starterCard);
//    }

    public Mission getPersonalMission(){ //ritorna la mission selezionata
        return selectedPersonalMission;
    }

    public void addHandCard(Card drawnCard) {
        try {
            hand.addCard(drawnCard);
            notifyObserver(new NetworkMessage(nickname, MessageType.CORRECT_DRAW_CARD, this.argsGenerator(hand)));
        } catch (PersonalizedException.InvalidAddCardException |
                 PersonalizedException.InvalidRequestTypeOfNetworkMessage e) {
            throw new RuntimeException(e);
        }
    }

    public String getNickname() {
        return nickname;
    }

    @Override
    public int executePersonalMission() {
        return selectedPersonalMission.ruleAlgorithmCheck(this);
    }

    @Override
    public void placeCard(int x, int y, int numCard, boolean isCardBack) throws PersonalizedException.InvalidPlacementException, PersonalizedException.InvalidPlaceCardRequirementException {
        Card playedCard;
        int placeResult = 0;

        try {
            playedCard = hand.popCard(numCard);
        } catch (PersonalizedException.InvalidNumPopCardException e) {
            throw new RuntimeException(e);
        } catch (PersonalizedException.InvalidPopCardException e) {
            throw new RuntimeException(e);
        }
        try {
            placeResult = gameMap.placeCard(x, y, playedCard, isCardBack);
            personalScoreBoardScore+=placeResult;
            try {
                notifyObserver(new NetworkMessage(nickname, MessageType.CORRECT_PLACEMENT, argsGenerator(getScoreResource())));
            } catch (PersonalizedException.InvalidRequestTypeOfNetworkMessage e) {
                throw new RuntimeException(e);
            }
            try {
                notifyObserver(new NetworkMessage(nickname, MessageType.SCORE_UPDATE, argsGenerator(personalScoreBoardScore)));
            } catch (PersonalizedException.InvalidRequestTypeOfNetworkMessage e) {
                throw new RuntimeException(e);
            }
        } catch (PersonalizedException.InvalidPlacementException e) {
            //ripiazza la carta nella mano
            try {
                hand.addCard(playedCard);
            } catch (PersonalizedException.InvalidAddCardException ex) {
                throw new RuntimeException(ex);
            }
            throw e; // Propagate the caught exception directly
        } catch (PersonalizedException.InvalidPlaceCardRequirementException e) {
            //ripiazza la carta nella mano
            try {
                hand.addCard(playedCard);
            } catch (PersonalizedException.InvalidAddCardException ex) {
                throw new RuntimeException(ex);
            }
            throw e; // Propagate the caught exception directly
            }
    }

    @Override
    public void addMissionScore(int value) {
        personalMissionTotalScore = personalMissionTotalScore + value;
        personalScoreBoardScore = personalScoreBoardScore + value;
    }

    @Override
    public boolean isPlayerAlive() {
        return alive;
    }

    @Override
    public void setPersonalMissions(Mission mission1, Mission mission2) {
        personalMission1 = mission1;
        personalMission2 = mission2;
    }

    //selection = 1 -> mission1, selection = 2 -> mission2
    @Override
    public void setPersonalMissionFinal(int selection) {
        if(selection == 1||selection ==2) {
            switch(selection){
                case 1:
                    selectedPersonalMission = personalMission1;
                case 2:
                    selectedPersonalMission = personalMission2;
            }
            try {
                notifyObserver(new NetworkMessage(nickname, MessageType.COM_ACK_TCP, "true"));
            } catch (PersonalizedException.InvalidRequestTypeOfNetworkMessage e) {
                throw new RuntimeException(e);
            }
        } else {
            System.err.println("Errore Selezione Missione Player");
            try {
                notifyObserver(new NetworkMessage(nickname, MessageType.COM_ACK_TCP,"false"));
            } catch (PersonalizedException.InvalidRequestTypeOfNetworkMessage e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void switchPlayerView(Player target) {
        playerView=target;
        try {
            notifyObserver(new NetworkMessage(nickname, MessageType.SWITCH_PLAYER_VIEW, argsGenerator(playerView.getGameMap())));
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
    public void setPawnColor(ColorType pawnColor) {
        this.pawnColor = pawnColor;
    }

    @Override
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    @Override
    public void setStatus(boolean status){
        alive = status;
        try {
            notifyObserver(new NetworkMessage(nickname, MessageType.STATUS_PLAYER_CHANGE, Boolean.toString(status)));
        } catch (PersonalizedException.InvalidRequestTypeOfNetworkMessage e) {
            throw new RuntimeException(e);
        }
    }

    public PlayerScoreResource getScoreResource() {
        return scoreResource;
    }

    public void setScoreResource(PlayerScoreResource scoreResource) {
        this.scoreResource = scoreResource;
    }

    public GamePlayerMap getGameMap() {
        return gameMap;
    }

    public void setGameMap(GamePlayerMap gameMap) {
        this.gameMap = gameMap;
    }

    public Player(String nick, ColorType color){
        nickname = nick;
        pawnColor = color;
        personalScoreBoardScore = 0;
        personalMissionTotalScore = 0;
        personalMission1 = null;
        personalMission2 = null;
        selectedPersonalMission = null;
        playerView = this;
        scoreResource = new PlayerScoreResource();
        gameMap = new GamePlayerMap(scoreResource);
        hand = new Hand();
        pawnImg = null;//TODO:mettere case con inserimento immagine
        alive = true;
    }

    public Player(String nick, ColorType color, Observer observer){
        nickname = nick;
        pawnColor = color;
        personalScoreBoardScore = 0;
        personalMissionTotalScore = 0;
        personalMission1 = null;
        personalMission2 = null;
        selectedPersonalMission = null;
        playerView = this;
        scoreResource = new PlayerScoreResource();
        gameMap = new GamePlayerMap(scoreResource);
        hand = new Hand();
        pawnImg = null;//TODO:mettere case con inserimento immagine
        alive = true;
        addObserver(observer);
    }

    public Hand getHand() {
        return hand;
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