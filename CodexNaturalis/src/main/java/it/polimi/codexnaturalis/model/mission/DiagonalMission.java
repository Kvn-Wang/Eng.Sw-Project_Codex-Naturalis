package it.polimi.codexnaturalis.model.mission;

import it.polimi.codexnaturalis.model.enumeration.ResourceType;
import it.polimi.codexnaturalis.model.player.PlayerScore;
import it.polimi.codexnaturalis.model.shop.card.Card;

public class DiagonalMission extends Mission implements ControlMissionMethod  {
    private final boolean isLeftToRight;
    private final ResourceType typeOfResource;

    public DiagonalMission(int rewardPoint, boolean isLeftToRight, ResourceType typeOfResource) {
        pointPerCondition = rewardPoint;
        this.isLeftToRight = isLeftToRight;
        this.typeOfResource = typeOfResource;
    }

    @Override
    public int ruleAlgorithmCheck(Card[][] mapArray) {
        int match = 0;
        for(int i = 0; i < (mapArray.length - 2); i++){
            for(int j = 0; j < (mapArray.length - 2); j++){
                if(mapArray[i][j]!=null){
                    if(mapArray[i][j]./*getResource*/==typeOfResource && !mapArray[i][j].getIsUsedForCheckRule()){
                        if(isLeftToRight){
                            if (mapArray[i][j + 1]./*getResource*/ == typeOfResource && mapArray[i][j + 2]./*getResource*/ == typeOfResource) {
                                match++;
                                mapArray[i][j].setUsedForCheckRule(true);
                                mapArray[i][j + 1].setUsedForCheckRule(true);
                                mapArray[i][j + 2].setUsedForCheckRule(true);
                            }
                        }
                        else{
                            if (mapArray[i + 1][j]./*getResource*/ == typeOfResource /*&& !mapArray[i+1][j].getIsUsedForCheckRule()*/ && mapArray[i + 2][j]./*getResource*/ == typeOfResource /*&& !mapArray[i+2][j].getIsUsedForCheckRule()*/) {
                                match++;
                                mapArray[i][j].setUsedForCheckRule(true);
                                mapArray[i + 1][j].setUsedForCheckRule(true);
                                mapArray[i + 2][j].setUsedForCheckRule(true);
                            }
                        }
                    }
                }
            }
        }
        return match*pointPerCondition;
    }

    @Override
    public int ruleAlgorithmCheck(PlayerScore playerScore) {
        return 0;
    }
}
