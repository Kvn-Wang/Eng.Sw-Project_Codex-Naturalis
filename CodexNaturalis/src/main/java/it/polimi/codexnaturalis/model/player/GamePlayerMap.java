package it.polimi.codexnaturalis.model.player;

import it.polimi.codexnaturalis.model.shop.card.Card;
import it.polimi.codexnaturalis.utils.UtilCostantValue;

public class GamePlayerMap {
    private Card[][] mapArray;
    private PlayerScore playerscore;

    public GamePlayerMap(PlayerScore playerScore) {
        this.playerscore = playerScore;
        this.mapArray = new Card[UtilCostantValue.lunghezzaMaxMappa][UtilCostantValue.lunghezzaMaxMappa];
    }

    public int placeCard(int x, int y, Card card) { return 0; }
    private boolean checkValidPosition(int x, int y) { return false; }
    private boolean isCardObjective(Card card) { return false; }
    private int checkAddPointCard(Card card) { return 0; }
    public Card getCard(int x, int y) { return null; }
}
