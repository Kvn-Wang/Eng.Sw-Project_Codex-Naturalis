package it.polimi.codexnaturalis.model.player;

import it.polimi.codexnaturalis.model.enumeration.CardCorner;
import it.polimi.codexnaturalis.model.enumeration.ResourceType;
import it.polimi.codexnaturalis.model.mission.Mission;
import it.polimi.codexnaturalis.model.shop.card.Card;
import it.polimi.codexnaturalis.model.shop.card.ResourceCard;
import it.polimi.codexnaturalis.model.shop.card.StarterCard;

public class Player implements PlayerInterface {
    private String nickname;
    private int personalScoreBoardScore;
    private int personalMissionTotalScore;
    private String pawnColor;
    private boolean alive;
    private Mission personalMission1;
    private Mission personalMission2;
    private Mission selectedPersonalMission;
    private Player playerView;
    private PlayerScoreResource scoreResource;
    private GamePlayerMap gameMap;
    private Hand hand;
    private String pawnImg;

    public Player() {
        scoreResource = new PlayerScoreResource();
        gameMap = new GamePlayerMap(scoreResource);
        hand = new Hand();
        boolean alive = true;
    }

    public Mission getPersonalMission(int numMission){
        if(numMission == 1)
            return personalMission1;
        else if (numMission == 2) {
            return personalMission2;
        }
        else
            return null;
    }

    public void addHandCard(Card drawnCard) {
        hand.addCard(drawnCard);
    }
    @Override
    public void setPersonalMissionChoice(Mission selectedPersonalMission) {
        this.selectedPersonalMission = selectedPersonalMission;
    }

    public String getNickname() {
        return nickname;
    }

    @Override
    public int executePersonalMission() {
        return selectedPersonalMission.ruleAlgorithmCheck(this);
    }

    @Override
    public void placeCard(int x, int y, int numCard) {
        Card playedCard;
        int PlaceResult;

        playedCard = hand.popCard(numCard);
        PlaceResult = gameMap.placeCard(x, y, playedCard);

        if(PlaceResult == -1) {
            hand.addCard(playedCard);
            System.err.println("Giocata non Valida");
        } else if(PlaceResult > 0) {
            personalScoreBoardScore+=PlaceResult;
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
        if(selection == 1) {
            selectedPersonalMission = personalMission1;
        } else if(selection == 2) {
            selectedPersonalMission = personalMission2;
        } else {
            System.err.println("Errore Selezione Missione Player");
        }
    }

    @Override
    public void switchPlayerView(Player target) {
        playerView=target;
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
    public String getPawnColor() {
        return pawnColor;
    }

    @Override
    public void setPawnColor(String pawnColor) {
        this.pawnColor = pawnColor;
    }

    @Override
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    @Override
    public void setStatus(boolean status){
        alive = status;
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

    public Player(String nick){
        nickname=nick;
        personalScoreBoardScore=0;
        personalMissionTotalScore=0;
        pawnColor=null;
        alive=true;
        personalMission1=null;
        personalMission2=null;
        selectedPersonalMission=null;
        playerView=this;
    }

    public String getPawnImg() {
        return pawnImg;
    }

    public void setPawnImg(String pawnImg) {
        this.pawnImg = pawnImg;
    }

    protected ResourceType checkCornerCardCorner(Card card, CardCorner corner){
        switch(corner){
            case NORTH:
                if(!card.getIsBack()) {
                    return card.getFrontNorthResource();
                } else if(card.getClass() == StarterCard.class){
                    return ((StarterCard) card).getBackNorthResource();
                }else{
                    return ResourceType.NONE;
                }
            case SOUTH:
                if(!card.getIsBack()) {
                    return card.getFrontSouthResource();
                } else if(card.getClass() == StarterCard.class){
                    return ((StarterCard) card).getBackSouthResource();
                }else{
                    return ResourceType.NONE;
                }
            case EAST:
                if(!card.getIsBack()) {
                    return card.getFrontEastResource();
                } else if(card.getClass() == StarterCard.class){
                    return ((StarterCard) card).getBackEastResource();
                }else{
                    return ResourceType.NONE;
                }
            case WEST:
                if(!card.getIsBack()) {
                    return card.getFrontWestResource();
                } else if(card.getClass() == StarterCard.class){
                    return ((StarterCard) card).getBackWestResource();
                }else{
                    return ResourceType.NONE;
                }
        }
        return null;
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
