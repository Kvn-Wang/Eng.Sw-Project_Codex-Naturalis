package it.polimi.codexnaturalis.model.mission;

import it.polimi.codexnaturalis.model.enumeration.ResourceType;
import it.polimi.codexnaturalis.model.player.PlayerScore;
import it.polimi.codexnaturalis.model.shop.card.Card;

public class ResourceMission extends Mission implements ControlMissionMethod {
    private final int numberOfSymbols;
    private final ResourceType[] typeOfResource;

    public ResourceMission(int rewardPoint, int numberOfSymbols, ResourceType[] typeOfResource) {
        pointPerCondition = rewardPoint;
        this.numberOfSymbols = numberOfSymbols;
        this.typeOfResource = typeOfResource;
    }

    @Override
    public int ruleAlgorithmCheck(Card[][] mapArray) {
        return 0;
    }

    @Override
    public int ruleAlgorithmCheck(PlayerScore playerScore) {
        if(typeOfResource.length!=1){
            return (playerScore.getScore(typeOfResource[0])/numberOfSymbols)*pointPerCondition;
        }
        else if(typeOfResource.length==3){
            int score1 = playerScore.getScore(typeOfResource[0])/numberOfSymbols;
            int score2 = playerScore.getScore(typeOfResource[1])/numberOfSymbols;
            int score3 = playerScore.getScore(typeOfResource[2])/numberOfSymbols;
            if (score1 <= score2 && score1 <= score3) {
                return score1*pointPerCondition;
            } else if (score2 <= score3 && score2 <= score1) {
                return score2*pointPerCondition;
            } else {
                return score3*pointPerCondition;
            }
        }
        else{
            return -1;
        }
    }
}
