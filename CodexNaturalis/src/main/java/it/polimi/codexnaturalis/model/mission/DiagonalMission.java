package it.polimi.codexnaturalis.model.mission;

import it.polimi.codexnaturalis.model.enumeration.MissionType;
import it.polimi.codexnaturalis.model.enumeration.ResourceType;
import it.polimi.codexnaturalis.model.player.Player;
import it.polimi.codexnaturalis.model.shop.card.Card;

public class DiagonalMission extends Mission  {
    private Card[][] mapArray;
    private final boolean isLeftToRight;
    private final ResourceType resourceType;

    public DiagonalMission(int pngNumber, int pointPerCondition, boolean isLeftToRight, ResourceType typeOfResource) {
        super(pngNumber, pointPerCondition);
        this.isLeftToRight = isLeftToRight;
        this.resourceType = typeOfResource;
    }

    @Override
    public int ruleAlgorithmCheck(Player player) {
        mapArray = player.getGameMap().getMapArray();
        int match = 0;
        if(isLeftToRight){
            for(int i = 0; i < (mapArray.length - 2); i++) {
                for (int j = 0; j < (mapArray[0].length); j++) {
                    if (mapArray[i][j] != null) {
                        if (mapArray[i][j].getCardColor() == resourceType && !usedCardArray[i][j]) {
                            if (mapArray[i + 1][j].getCardColor() == resourceType && mapArray[i + 2][j].getCardColor() == resourceType) {
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
                for (int j = mapArray[0].length -  1; j >= 2; j--) {
                    if (mapArray[i][j] != null) {
                        if (mapArray[i][j].getCardColor() == resourceType && !usedCardArray[i][j]) {
                            if (mapArray[i][j - 1] != null && mapArray[i][j - 2] != null && mapArray[i][j - 1].getCardColor() == resourceType && mapArray[i][j - 2].getCardColor() == resourceType) {
                                match++;
                                usedCardArray[i][j] = true;
                                usedCardArray[i][j - 1] = true;
                                usedCardArray[i][j - 2] = true;
                            }
                        }
                    }
                }
            }
        }
        return match*pointPerCondition;
    }

    public Card[][] getMapArray() {
        return mapArray;
    }

    public boolean getIsLeftToRight() {
        return isLeftToRight;
    }

    public ResourceType getResourceType() {
        return resourceType;
    }

    public MissionType getMissionType() {
        return MissionType.DIAGONAL;
    }
}
