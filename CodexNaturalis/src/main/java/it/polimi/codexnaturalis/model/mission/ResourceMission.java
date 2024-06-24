package it.polimi.codexnaturalis.model.mission;

import it.polimi.codexnaturalis.model.enumeration.MissionType;
import it.polimi.codexnaturalis.model.enumeration.ResourceType;
import it.polimi.codexnaturalis.model.player.Player;
import it.polimi.codexnaturalis.model.player.PlayerScoreResource;
import it.polimi.codexnaturalis.model.shop.card.Card;

import java.io.Serializable;

/**
 * The type Resource mission.
 */
public class ResourceMission extends Mission implements Serializable {
    private PlayerScoreResource playerScoreResource;
    private int numberOfSymbols;
    private ResourceType[] typeOfResource;

    /**
     * Instantiates a new Resource mission.
     *
     * @param pngNumber         the png number
     * @param pointPerCondition the point per condition
     * @param numberOfSymbols   the number of symbols
     * @param typeOfResource    the type of resource
     */
    public ResourceMission(int pngNumber, int pointPerCondition, int numberOfSymbols, ResourceType[] typeOfResource) {
        super(pngNumber, pointPerCondition);
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

    @Override
    public MissionType getMissionType() {
        return MissionType.RESOURCE;
    }

    /**
     * Gets number of symbols.
     *
     * @return the number of symbols
     */
    public int getNumberOfSymbols() {
        return numberOfSymbols;
    }

    /**
     * Get type of resource resource type [ ].
     *
     * @return the resource type [ ]
     */
    public ResourceType[] getTypeOfResource() {
        return typeOfResource;
    }
}
