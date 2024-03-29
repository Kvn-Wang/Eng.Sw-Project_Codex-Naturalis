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
    public int ruleAlgorithmCheck(Card[][] mapArray) {
        return 0;
    }

    @Override
    public int ruleAlgorithmCheck(PlayerScore playerScore) {
        return 0;
    }
}
