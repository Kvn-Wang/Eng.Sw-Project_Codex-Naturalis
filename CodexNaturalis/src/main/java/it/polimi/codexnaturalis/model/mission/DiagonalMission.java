package it.polimi.codexnaturalis.model.mission;

import it.polimi.codexnaturalis.model.enumeration.ResourceType;
import it.polimi.codexnaturalis.model.player.Player;
import it.polimi.codexnaturalis.model.player.PlayerScoreResource;
import it.polimi.codexnaturalis.model.shop.card.Card;

public class DiagonalMission extends Mission implements ControlMissionMethod  {
    private Card[][] mapArray;
    private final boolean isLeftToRight;
    private final ResourceType typeOfResource;

    public DiagonalMission(int rewardPoint, boolean isLeftToRight, ResourceType typeOfResource) {
        super();
        pointPerCondition = rewardPoint;
        this.isLeftToRight = isLeftToRight;
        this.typeOfResource = typeOfResource;
    }

    @Override
    public int ruleAlgorithmCheck(Player player) {
        mapArray = player.getGameMap().getMapArray();
        int match = 0;

        if(isLeftToRight){
            for(int i = 0; i < (mapArray.length - 2); i++) {
                for (int j = 0; j < (mapArray[0].length); j++) {
                    if (mapArray[i][j] != null) {
                        if (mapArray[i][j].getColor() == typeOfResource && !usedCardArray[i][j]) {
                            if (mapArray[i + 1][j].getColor() == typeOfResource && mapArray[i + 2][j].getColor() == typeOfResource) {
                                match++;
                                usedCardArray[i][j] = true;
                                usedCardArray[i + 1][j] = true;
                                usedCardArray[i + 2][j] = true;
                            }
                        }
                    }
                }
            }
        } else {
            for(int i = 0; i < (mapArray.length); i++) {
                for (int j = 2; j < (mapArray[0].length); j++) {
                    if (mapArray[i][j] != null) {
                        if (mapArray[i][j].getColor() == typeOfResource && !usedCardArray[i][j]) {
                            if (mapArray[i][j - 1].getColor() == typeOfResource && mapArray[i][j - 2].getColor() == typeOfResource) {
                                match++;
                                usedCardArray[i][j] = true;
                                usedCardArray[i - 1][j] = true;
                                usedCardArray[i - 2][j] = true;
                            }
                        }
                    }
                }
            }
        }
        return match*pointPerCondition;
    }
}
