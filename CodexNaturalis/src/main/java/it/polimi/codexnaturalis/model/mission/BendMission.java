package it.polimi.codexnaturalis.model.mission;

import it.polimi.codexnaturalis.model.enumeration.CardCorner;
import it.polimi.codexnaturalis.model.enumeration.MissionType;
import it.polimi.codexnaturalis.model.enumeration.ResourceType;
import it.polimi.codexnaturalis.model.player.Player;
import it.polimi.codexnaturalis.model.shop.card.Card;
import it.polimi.codexnaturalis.utils.UtilCostantValue;

import java.io.Serializable;

/**
 * The type Bend mission.
 */
public class BendMission extends Mission implements Serializable {
    private Card[][] mapArray;
    private CardCorner decorationPosition;
    private ResourceType pillarResource;
    private ResourceType decorationResource;
    /**
     * The Middle.
     */
    int middle = UtilCostantValue.lunghezzaMaxMappa/2;

    /**
     * Instantiates a new Bend mission.
     *
     * @param pngNumber          the png
     * @param pointPerCondition  the point per condition
     * @param pillarResource     the pillar resource
     * @param decorationResource the decoration resource
     * @param decorationPosition the decoration position
     */
    public BendMission(int pngNumber, int pointPerCondition, ResourceType pillarResource, ResourceType decorationResource, CardCorner decorationPosition) {
        super(pngNumber, pointPerCondition);
        this.decorationPosition = decorationPosition;
        this.pillarResource = pillarResource;
        this.decorationResource = decorationResource;
    }

    @Override
    public int ruleAlgorithmCheck(Player player) {//TODO: setcheck delle carte del pillar e verso di ricerca
        mapArray = player.getGameMap().getMapArray();
        int match = 0;

        switch (decorationPosition) {
            case EAST:
                for (int i = 0; i < (mapArray.length - 1); i++) {
                    for (int j = 2; j < (mapArray[0].length); j++) {
                        if (mapArray[i][j] != null) {
                            if (mapArray[i][j].getCardColor() == decorationResource && !usedCardArray[i][j]) {
                                if (mapArray[i][j - 1] != null && mapArray[i][j - 1].getCardColor() == pillarResource && mapArray[i + 1][j - 2] != null &&  mapArray[i + 1][j - 2].getCardColor() == pillarResource) {
                                    match++;
                                    usedCardArray[i][j] = true;
                                    usedCardArray[i][j - 1] = true;
                                    usedCardArray[i + 1][j - 2] = true;
                                }
                            }
                        }
                    }
                }
                break;
            case NORTH:
                for (int i = 0; i < (mapArray.length - 2); i++) {
                    for (int j = 1; j < (mapArray[0].length); j++) {
                        if (mapArray[i][j] != null) {
                            if (mapArray[i][j].getCardColor() == decorationResource && !usedCardArray[i][j]) {
                                if (mapArray[i+1][j] != null && mapArray[i + 1][j].getCardColor() == pillarResource && mapArray[i+2][j-1] != null && mapArray[i + 2][j - 1].getCardColor() == pillarResource) {
                                    match++;
                                    usedCardArray[i][j] = true;
                                    usedCardArray[i + 1][j] = true;
                                    usedCardArray[i + 2][j - 1] = true;
                                }
                            }
                        }
                    }
                }
                break;
            case WEST:
                for (int i = 1; i < (mapArray.length); i++) {
                    for (int j = 0; j < (mapArray[0].length - 2); j++) {
                        if (mapArray[i][j] != null) {
                            if (mapArray[i][j].getCardColor() == decorationResource && !usedCardArray[i][j]) {
                                if (mapArray[i][j + 1] != null && mapArray[i][j + 1].getCardColor() == pillarResource && mapArray[i - 1][j + 2] != null && mapArray[i - 1][j + 2].getCardColor() == pillarResource) {
                                    match++;
                                    usedCardArray[i][j] = true;
                                    usedCardArray[i][j + 1] = true;
                                    usedCardArray[i - 1][j + 2] = true;
                                }
                            }
                        }
                    }
                }
                break;
            case SOUTH:
                for (int i = 0; i < (mapArray.length); i++) {
                    for (int j = 0; j < (mapArray[0].length); j++) {
                        if (mapArray[i][j] != null) {
                            if (mapArray[i][j].getCardColor() == decorationResource && !usedCardArray[i][j]) {
                                if (mapArray[i-1][j] != null && mapArray[i - 1][j].getCardColor() == pillarResource && mapArray[i-2][j+1] != null && mapArray[i - 2][j + 1].getCardColor() == pillarResource) {
                                    match++;
                                    usedCardArray[i][j] = true;
                                    usedCardArray[i - 1][j] = true;
                                    usedCardArray[i - 2][j + 1] = true;
                                }
                            }
                        }
                    }
                }
                break;
            default:
                throw new RuntimeException("Posizione carta decoration non valida: "+decorationPosition);
        }
        return match*pointPerCondition;
    }

    /**
     * Gets decoration position.
     *
     * @return the decoration position
     */
    public CardCorner getDecorationPosition() {
        return decorationPosition;
    }

    /**
     * Gets pillar resource.
     *
     * @return the pillar resource
     */
    public ResourceType getPillarResource() {
        return pillarResource;
    }

    /**
     * Gets decoration resource.
     *
     * @return the decoration resource
     */
    public ResourceType getDecorationResource() {
        return decorationResource;
    }

    public MissionType getMissionType() {
        return MissionType.BEND;
    }
}
