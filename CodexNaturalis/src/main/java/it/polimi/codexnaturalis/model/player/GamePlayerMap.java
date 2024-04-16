package it.polimi.codexnaturalis.model.player;

import it.polimi.codexnaturalis.model.shop.card.Card;
import it.polimi.codexnaturalis.model.shop.card.ObjectiveCard;
import it.polimi.codexnaturalis.model.shop.card.ResourceCard;
import it.polimi.codexnaturalis.model.shop.card.StarterCard;
import it.polimi.codexnaturalis.utils.PersonalizedException;
import it.polimi.codexnaturalis.utils.UtilCostantValue;

public class GamePlayerMap {
    private Card[][] mapArray;
    private PlayerScoreResource playerScoreCard;

    public GamePlayerMap(PlayerScoreResource playerScoreResource) {
        this.playerScoreCard = playerScoreResource;
        this.mapArray = new Card[UtilCostantValue.lunghezzaMaxMappa][UtilCostantValue.lunghezzaMaxMappa];

    }

    //la chiamata a placeCard ritornerà
    //throw exception come valore speciale per dire che non vale piazzare la carta,
    // = 0 come valore per indicare che la carta è stata aggiunta senza aggiunta eventuali di punti (carte obbiettivo o carte risorsa front),
    // oppure > 0 per indicare che la carta piazzata deve aggiungere punti equivalente al valore di ritorno al punteggio del player
    public int placeCard(int x, int y, Card card) throws PersonalizedException.InvalidPlacementException {
        if(checkValidPosition(x,y)){
            //TODO: controllo is placeable
            mapArray[x][y] = card;

            //TODO: funzione get resources così le aggiungo tutte in una volta alla scorecard
            //TODO: funzione get point con argomento scoreCard e mappa?

            if(card.getClass() == ResourceCard.class){
                playerScoreCard.addScore(((ResourceCard) card).getBackCentralResource());
                return ((ResourceCard) card).getFrontCardPoint();
            }else if(card.getClass() == ObjectiveCard.class){
                return ((ObjectiveCard) card).getPointPerCondition();
            }else if(card.getClass() == StarterCard.class) {

                return 0;
            }
        }else {
            throw new PersonalizedException.InvalidPlacementException();
        }

        //temporaneo, messo a caso
        return 0;
    }

    //TODO: controlla che le celle attorno almeno una sia "piena" e che l'angolo sia valido per piazzare una carta
    private boolean checkValidPosition(int x, int y) { return false; }

    public Card getCard(int x, int y) { return mapArray[x][y]; }

    public Card[][] getMapArray() {
        return mapArray;
    }

    public PlayerScoreResource getPlayerScoreCard() {
        return playerScoreCard;
    }
}
