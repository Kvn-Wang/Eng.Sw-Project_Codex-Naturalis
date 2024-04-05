package it.polimi.codexnaturalis.model.mission;

import it.polimi.codexnaturalis.utils.UtilCostantValue;

public abstract class Mission {
    protected String png;
    protected int pointPerCondition;
    protected boolean usedCardArray[][];

    public Mission() {
        usedCardArray = new boolean[UtilCostantValue.lunghezzaMaxMappa][UtilCostantValue.lunghezzaMaxMappa];

        //inizializzazione usedArray
        for(int i = 0; i < (usedCardArray.length); i++) {
            for (int j = 0; j < (usedCardArray[0].length); j++) {
                usedCardArray[i][j] = false;
            }
        }
    }
}
