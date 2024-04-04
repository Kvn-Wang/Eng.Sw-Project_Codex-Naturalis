package it.polimi.codexnaturalis.model.mission;

import it.polimi.codexnaturalis.model.enumeration.ResourceType;
import it.polimi.codexnaturalis.model.player.PlayerScore;
import it.polimi.codexnaturalis.model.shop.card.Card;

public class BendMission extends Mission implements ControlMissionMethod  {
    private String decorationPosition;
    private ResourceType pillarResource;
    private ResourceType decorationResource;

    public BendMission(int rewardPoint, ResourceType pillarResource, ResourceType decorationResource, String decorationPosition) {
        pointPerCondition = rewardPoint;
        this.decorationPosition = decorationPosition;
        this.pillarResource = pillarResource;
        this.decorationResource = decorationResource;
    }

    @Override
    public int ruleAlgorithmCheck(Card[][] mapArray) {//TODO: setcheck delle carte del pillar e verso di ricerca
        int match = 0;
        switch (decorationPosition) {
            case "EST":
                for (int i = 0; i < (mapArray.length - 1); i++) {
                    for (int j = 2; j < (mapArray[0].length); j++) {
                        if (mapArray[i][j] != null) {
                            if (mapArray[i][j].getColor() == decorationResource && !mapArray[i][j].getIsUsedForCheckRule()) {
                                if (mapArray[i][j - 1].getColor() == pillarResource && mapArray[i + 1][j - 2].getColor() == pillarResource) {
                                    match++;
                                    mapArray[i][j].setUsedForCheckRule(true);
                                    mapArray[i][j - 1].setUsedForCheckRule(true);
                                    mapArray[i + 1][j - 2].setUsedForCheckRule(true);
                                }
                            }
                        }
                    }
                }
                break;
            case "NORTH":
                for (int i = 0; i < (mapArray.length - 2); i++) {
                    for (int j = 1; j < (mapArray[0].length); j++) {
                        if (mapArray[i][j] != null) {
                            if (mapArray[i][j].getColor() == decorationResource && !mapArray[i][j].getIsUsedForCheckRule()) {
                                if (mapArray[i + 1][j].getColor() == pillarResource && mapArray[i + 2][j - 1].getColor() == pillarResource) {
                                    match++;
                                    mapArray[i][j].setUsedForCheckRule(true);
                                    mapArray[i + 1][j].setUsedForCheckRule(true);
                                    mapArray[i + 2][j - 1].setUsedForCheckRule(true);
                                }
                            }
                        }
                    }
                }
                break;
            case "WEST":
                for (int i = 1; i < (mapArray.length); i++) {
                    for (int j = 0; j < (mapArray[0].length - 2); j++) {
                        if (mapArray[i][j] != null) {
                            if (mapArray[i][j].getColor() == decorationResource && !mapArray[i][j].getIsUsedForCheckRule()) {
                                if (mapArray[i][j + 1].getColor() == pillarResource && mapArray[i - 1][j + 2].getColor() == pillarResource) {
                                    match++;
                                    mapArray[i][j].setUsedForCheckRule(true);
                                    mapArray[i][j + 1].setUsedForCheckRule(true);
                                    mapArray[i - 1][j + 2].setUsedForCheckRule(true);
                                }
                            }
                        }
                    }
                }
                break;
            case "SOUTH":
                for (int i = 0; i < (mapArray.length); i++) {
                    for (int j = 0; j < (mapArray[0].length); j++) {
                        if (mapArray[i][j] != null) {
                            if (mapArray[i][j].getColor() == decorationResource && !mapArray[i][j].getIsUsedForCheckRule()) {
                                if (mapArray[i - 1][j].getColor() == pillarResource && mapArray[i - 2][j + 1].getColor() == pillarResource) {
                                    match++;
                                    mapArray[i][j].setUsedForCheckRule(true);
                                    mapArray[i - 1][j].setUsedForCheckRule(true);
                                    mapArray[i - 2][j + 1].setUsedForCheckRule(true);
                                }
                            }
                        }
                    }
                }
                break;
        }
        return match*pointPerCondition;
    }

    @Override
    public int ruleAlgorithmCheck(PlayerScore playerScore) {
        return 0;
    }
}
