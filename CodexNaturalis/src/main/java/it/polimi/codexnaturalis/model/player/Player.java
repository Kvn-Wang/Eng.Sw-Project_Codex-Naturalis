package it.polimi.codexnaturalis.model.player;

import it.polimi.codexnaturalis.model.mission.Mission;

public class Player {
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

    public void setSelectedPersonalMission(Mission selectedPersonalMission) {
        this.selectedPersonalMission = selectedPersonalMission;
    }

    public String getNickname() {
        return nickname;
    }
}
