package it.polimi.codexnaturalis.model.mission;

import it.polimi.codexnaturalis.model.enumeration.ResourceType;
import it.polimi.codexnaturalis.model.player.PlayerScore;
import it.polimi.codexnaturalis.model.shop.card.Card;

public class DiagonalMission extends Mission implements ControlMissionMethod  {
    private boolean isLeftToRight;
    private ResourceType typeOfResource;

    public DiagonalMission(int rewardPoint, boolean isLeftToRight, ResourceType typeOfResource) {
        pointPerCondition = rewardPoint;
        this.isLeftToRight = isLeftToRight;
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
