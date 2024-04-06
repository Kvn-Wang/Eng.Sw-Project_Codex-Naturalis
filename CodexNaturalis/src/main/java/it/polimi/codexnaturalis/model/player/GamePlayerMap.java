package it.polimi.codexnaturalis.model.player;

import it.polimi.codexnaturalis.model.shop.card.Card;
import it.polimi.codexnaturalis.utils.UtilCostantValue;

public class GamePlayerMap {
    private Card[][] mapArray;
    private PlayerScoreResource playerscore;

    public GamePlayerMap(PlayerScoreResource playerScoreResource) {
        this.playerscore = playerScoreResource;
        this.mapArray = new Card[UtilCostantValue.lunghezzaMaxMappa][UtilCostantValue.lunghezzaMaxMappa];
    }

    //la chiamata a placeCard ritornerà
    //-1 come valore speciale per dire che non vale piazzare la carta,
    // = 0 come valore per indicare che la carta è stata aggiunta senza aggiunta eventuali di punti (carte obbiettivo o carte risorsa front),
    // oppure > 0 per indicare che la carta piazzata deve aggiungere punti equivalente al valore di ritorno al punteggio del player
    public int placeCard(int x, int y, Card card) { return 0; }
    private boolean checkValidPosition(int x, int y) { return false; }
    private boolean isCardObjective(Card card) { return false; }
    private int checkAddPointCard(Card card) { return 0; }
    public Card getCard(int x, int y) { return null; }

    public Card[][] getMapArray() {
        return mapArray;
    }

    public void setMapArray(Card[][] mapArray) {
        this.mapArray = mapArray;
    }

    public PlayerScoreResource getPlayerscore() {
        return playerscore;
    }

    public void setPlayerscore(PlayerScoreResource playerscore) {
        this.playerscore = playerscore;
    }
}
