package it.polimi.codexnaturalis.model.mission;

import com.google.gson.annotations.SerializedName;
import it.polimi.codexnaturalis.model.player.Player;
import it.polimi.codexnaturalis.utils.UtilCostantValue;

public abstract class Mission {
    protected String pngNumber;
    protected int pointPerCondition;
    protected boolean usedCardArray[][];

    public Mission(String pngNumber, int pointPerCondition) {
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
}
