package it.polimi.codexnaturalis.model.mission;

import it.polimi.codexnaturalis.model.enumeration.MissionType;
import it.polimi.codexnaturalis.model.player.Player;
import it.polimi.codexnaturalis.utils.UtilCostantValue;

/**
 * The type Mission.
 */
public abstract class Mission {
    /**
     * The Png number.
     */
    protected int pngNumber;
    /**
     * The Point per condition.
     */
    protected int pointPerCondition;
    /**
     * The Used card array.
     */
    protected boolean usedCardArray[][];

    /**
     * empty constructor, DO NOT remove, useful for unmarshaling during RMI setup exchange
     */
    public Mission(){}

    /**
     * Instantiates a new Mission.
     *
     * @param pngNumber         the png number
     * @param pointPerCondition the point per condition
     */
    public Mission(int pngNumber, int pointPerCondition) {
        this.pngNumber = pngNumber;
        this.pointPerCondition = pointPerCondition;
        usedCardArray = new boolean[UtilCostantValue.lunghezzaMaxMappa][UtilCostantValue.lunghezzaMaxMappa];
        //inizializzazione usedArray
        for(int i = 0; i < (usedCardArray.length); i++) {
            for (int j = 0; j < (usedCardArray[0].length); j++) {
                usedCardArray[i][j] = false;
            }
        }
    }

    /**
     * Rule algorithm check int.
     *
     * @param player the player
     * @return the int
     */
    public abstract int ruleAlgorithmCheck(Player player);

    /**
     * Gets png number.
     *
     * @return the png number
     */
    public int getPngNumber() {
        return pngNumber;
    }

    /**
     * Gets point per condition.
     *
     * @return the point per condition
     */
    public int getPointPerCondition() {
        return pointPerCondition;
    }

    /**
     * Gets mission type.
     *
     * @return the mission type
     */
    public abstract MissionType getMissionType();
}
