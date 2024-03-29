package it.polimi.codexnaturalis.model.mission;

import it.polimi.codexnaturalis.model.enumeration.ResourceType;
import it.polimi.codexnaturalis.model.player.PlayerScore;
import it.polimi.codexnaturalis.model.shop.card.Card;

public class ResourceMission extends Mission implements ControlMissionMethod {
    private int numberOfSymbols;
    private ResourceType[] typeOfResource;

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
        return 0;
    }
}
