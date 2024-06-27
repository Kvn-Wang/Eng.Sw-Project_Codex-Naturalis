package it.polimi.codexnaturalis.view.VirtualModel;

import it.polimi.codexnaturalis.model.enumeration.ColorType;
import it.polimi.codexnaturalis.model.shop.card.Card;
import it.polimi.codexnaturalis.utils.UtilCostantValue;

public class PlayerData {
    private Card[][] map;
    private int intScoreBoardScore;
    private ColorType playerColor;
    private int placedCardOrder;

    public PlayerData() {
        //inizializzazione mappa degli altri player
        map = new Card[UtilCostantValue.lunghezzaMaxMappa][UtilCostantValue.lunghezzaMaxMappa];
        for(int i = 0; i < UtilCostantValue.lunghezzaMaxMappa; i++) {
            for (int j = 0; j < UtilCostantValue.lunghezzaMaxMappa; j++) {
                map[i][j] = null;
            }
        }

        intScoreBoardScore = 0;
        placedCardOrder = 0;
        playerColor = null;
    }

    public void setPlayerColor(ColorType playerColor) {
        this.playerColor = playerColor;
    }

    public int getIntScoreBoardScore() {
        return intScoreBoardScore;
    }

    public ColorType getPlayerColor(){
        return playerColor;
    }

    public Card[][] getMap() {
        return map;
    }

    public void playCard(int x, int y, Card card) {
        map[x][y] = card;
        map[x][y].setPlacedOrder(placedCardOrder);
        placedCardOrder++;
    }

    public void setIntScoreBoardScore(int intScoreBoardScore) {
        this.intScoreBoardScore = intScoreBoardScore;
    }
}
