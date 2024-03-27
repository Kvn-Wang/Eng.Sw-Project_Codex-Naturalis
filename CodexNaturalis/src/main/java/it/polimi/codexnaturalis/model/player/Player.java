package it.polimi.codexnaturalis.model.player;

import it.polimi.codexnaturalis.model.mission.Mission;
import it.polimi.codexnaturalis.model.shop.card.Card;

public class Player implements PlayerInterface {
    private String nickname;
    private int personalScore;
    private String pawnColor;
    private boolean alive;
    private Mission personalMission1;
    private Mission personalMission2;
    private Mission selectedPersonalMission;
    private Player playerView;

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
    public int executePersonalMission(Card[][] mapArray) {
        return 0;
    }

    @Override
    public void placeCard(int x, int y, int numCard) {

    }

    @Override
    public void addScore(int Value) {

    }

    @Override
    public boolean isPlayerAlive() {
        return false;
    }

    @Override
    public void setPersonalMissionFinal() {

    }

    @Override
    public void switchPlayerView() {

    }

}
