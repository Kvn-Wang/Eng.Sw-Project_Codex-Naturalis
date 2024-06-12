package it.polimi.codexnaturalis.view.TUI;

import it.polimi.codexnaturalis.model.enumeration.MissionType;
import it.polimi.codexnaturalis.model.enumeration.ResourceType;
import it.polimi.codexnaturalis.model.mission.BendMission;
import it.polimi.codexnaturalis.model.mission.DiagonalMission;
import it.polimi.codexnaturalis.model.mission.Mission;
import it.polimi.codexnaturalis.model.mission.ResourceMission;

public class PrintMissionClass {
    public static void printMission(Mission mission) {
        switch (mission.getMissionType()){
            case MissionType.RESOURCE:
                ResourceMission resourceMission = (ResourceMission) mission;
                printResourceMission(resourceMission);
            case MissionType.BEND:
                BendMission bendMission = (BendMission) mission;
                printBendMission(bendMission);
            case MissionType.DIAGONAL:
                DiagonalMission diagMission = (DiagonalMission) mission;
                printDiagonalMission(diagMission);
        }
    }

    private static void printResourceMission(ResourceMission mission) {
        System.out.println(PrintSymbols.convertMultipleResourceType(mission.getTypeOfResource()));
    }

    private static void printBendMission(BendMission mission) {
        String redSquare = "U+1F7E5";
    }

    private static void printDiagonalMission(DiagonalMission mission) {

    }
}
