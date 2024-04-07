package it.polimi.codexnaturalis.model.player;

import it.polimi.codexnaturalis.model.mission.Mission;
import it.polimi.codexnaturalis.model.shop.card.Card;

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
        int esitoGiocata;

        playedCard = hand.popCard(numCard);
        esitoGiocata = gameMap.placeCard(x, y, playedCard);

        if(esitoGiocata == -1) {
            hand.addCard(playedCard);
            System.err.println("Giocata non Valida");
        } else if(esitoGiocata > 0) {
            personalScoreBoardScore+=esitoGiocata;
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
    public void switchPlayerView() {

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
}
