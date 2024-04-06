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

    public Player() {
        scoreResource = new PlayerScoreResource();
        gameMap = new GamePlayerMap(scoreResource);
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

    public void drawCard(Card drawnCard){

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
        return 0;
    }

    @Override
    public void placeCard(int x, int y, int numCard) {

    }

    @Override
    public void addMissionScore(int value) {
        personalMissionTotalScore = personalMissionTotalScore + value;
        personalScoreBoardScore = personalScoreBoardScore + value;
    }

    @Override
    public boolean isPlayerAlive() {
        return false;
    }

    @Override
    public void setPersonalMissions(Mission mission1, Mission mission2) {

    }

    @Override
    public void setPersonalMissionFinal() {

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
}
