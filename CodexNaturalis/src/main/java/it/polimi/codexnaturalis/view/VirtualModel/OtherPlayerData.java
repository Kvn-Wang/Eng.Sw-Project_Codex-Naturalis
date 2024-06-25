package it.polimi.codexnaturalis.view.VirtualModel;

import it.polimi.codexnaturalis.model.shop.card.Card;
import it.polimi.codexnaturalis.utils.UtilCostantValue;

import java.util.ArrayList;

public class OtherPlayerData {
    public Card[][] map;
    public int intScoreBoardScore;

    public OtherPlayerData() {
        //inizializzazione mappa degli altri player
        map = new Card[UtilCostantValue.lunghezzaMaxMappa][UtilCostantValue.lunghezzaMaxMappa];
        for(int i = 0; i < UtilCostantValue.lunghezzaMaxMappa; i++) {
            for (int j = 0; j < UtilCostantValue.lunghezzaMaxMappa; j++) {
                map[i][j] = null;
            }
        }

        intScoreBoardScore = 0;
    }

    public int getIntScoreBoardScore() {
        return intScoreBoardScore;
    }
}
