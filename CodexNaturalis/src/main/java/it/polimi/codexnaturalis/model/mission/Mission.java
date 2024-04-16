package it.polimi.codexnaturalis.model.mission;

import it.polimi.codexnaturalis.model.player.Player;
import it.polimi.codexnaturalis.utils.UtilCostantValue;

public abstract class Mission {
    protected int pngNumber;
    protected int pointPerCondition;
    protected boolean usedCardArray[][];

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

    public abstract int ruleAlgorithmCheck(Player player);

    public int getPngNumber() {
        return pngNumber;
    }

    public int getPointPerCondition() {
        return pointPerCondition;
    }
}
