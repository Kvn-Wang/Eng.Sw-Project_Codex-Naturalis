package it.polimi.codexnaturalis.model.mission;

import it.polimi.codexnaturalis.model.enumeration.ResourceType;
import it.polimi.codexnaturalis.model.player.Player;
import it.polimi.codexnaturalis.model.player.PlayerScoreResource;
import it.polimi.codexnaturalis.model.shop.card.Card;

public class ResourceMission extends Mission {
    private PlayerScoreResource playerScoreResource;
    private final int numberOfSymbols;
    private final ResourceType[] typeOfResource;

    public ResourceMission(int rewardPoint, int numberOfSymbols, ResourceType[] typeOfResource) {
        pointPerCondition = rewardPoint;
        this.numberOfSymbols = numberOfSymbols;
        this.typeOfResource = typeOfResource;
    }

    @Override
    public int ruleAlgorithmCheck(Player player) {
        playerScoreResource = player.getScoreResource();

        if(typeOfResource.length == 1){
            return (playerScoreResource.getScore(typeOfResource[0])/numberOfSymbols)*pointPerCondition;
        }
        else if(typeOfResource.length==3){
            int score1 = playerScoreResource.getScore(typeOfResource[0])/numberOfSymbols;
            int score2 = playerScoreResource.getScore(typeOfResource[1])/numberOfSymbols;
            int score3 = playerScoreResource.getScore(typeOfResource[2])/numberOfSymbols;
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
