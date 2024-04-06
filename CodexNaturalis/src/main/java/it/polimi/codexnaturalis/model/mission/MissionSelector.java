package it.polimi.codexnaturalis.model.mission;

import it.polimi.codexnaturalis.utils.UtilCostantValue;

public class MissionSelector {
    private String originalMissionFile;
    private String missionFile;

    public MissionSelector() {
        originalMissionFile = UtilCostantValue.pathToMissionJson;
    }

    public void shuffle() {
        //shuffle
    }
    public Mission drawFromFile() {
        Mission drawnMission = null;
        return drawnMission;
    }
}
