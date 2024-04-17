package it.polimi.codexnaturalis.model.player;

import it.polimi.codexnaturalis.model.enumeration.CardCorner;
import it.polimi.codexnaturalis.model.shop.card.Card;
import it.polimi.codexnaturalis.model.shop.card.ObjectiveCard;
import it.polimi.codexnaturalis.model.shop.card.ResourceCard;
import it.polimi.codexnaturalis.model.shop.card.StarterCard;
import it.polimi.codexnaturalis.utils.PersonalizedException;
import it.polimi.codexnaturalis.utils.UtilCostantValue;

public class GamePlayerMap {
    private Card[][] mapArray;
    private PlayerScoreResource playerScoreCard;

    public GamePlayerMap(PlayerScoreResource playerScoreResource, Card starterCard) {
        this.playerScoreCard = playerScoreResource;
        this.mapArray = new Card[UtilCostantValue.lunghezzaMaxMappa][UtilCostantValue.lunghezzaMaxMappa];

        //inizializzazione valori della mappa
        for(int i = 0; i < UtilCostantValue.lunghezzaMaxMappa; i++) {
            for(int j = 0; j < UtilCostantValue.lunghezzaMaxMappa; j++) {
                mapArray[i][j] = null;
            }
        }
        //posiziono la starterCard al centro
        mapArray[UtilCostantValue.lunghezzaMaxMappa/2][UtilCostantValue.lunghezzaMaxMappa/2] = starterCard;
    }

    //la chiamata a placeCard ritornerà
    //throw exception come valore speciale per dire che non vale piazzare la carta,
    // = 0 come valore per indicare che la carta è stata aggiunta senza aggiunta eventuali di punti (carte obbiettivo o carte risorsa front),
    // oppure > 0 per indicare che la carta piazzata deve aggiungere punti equivalente al valore di ritorno al punteggio del player
    // la carta è piazzabile se c'è una carta valida a fianco
    public int placeCard(int x, int y, Card card) throws PersonalizedException.InvalidPlacementException {
        if(checkValidPosition(x,y) > 0){
            //TODO: controllo is placeable (carte obj)
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

    // Controlla che le celle attorno almeno una sia "piena" e che l'angolo sia valido per piazzare una carta
    // se tutto va bene, ritorna il numero di carte adiacenti ad essa, altrimenti lancia una Exception
    private int checkValidPosition(int x, int y) throws PersonalizedException.InvalidPlacementException {
        int adiacentNumCard = 0;

        //controllo di adiacenza della carta
        if(!(mapArray[x + 1][y] == null)) {
            adiacentNumCard++;
        }
        if(!(mapArray[x - 1][y] == null)) {
            adiacentNumCard++;
        }
        if(!(mapArray[x][y + 1] == null)) {
            adiacentNumCard++;
        }
        if(!(mapArray[x][y - 1] == null)) {
            adiacentNumCard++;
        }

        if(adiacentNumCard == 0) {
            throw new PersonalizedException.InvalidPlacementException();
        }

        //controllo dei corner delle carte adiacenti
        if(mapArray[x + 1][y].getCardCorner(CardCorner.WEST) == null || mapArray[x - 1][y].getCardCorner(CardCorner.EAST) == null ||
                mapArray[x][y + 1].getCardCorner(CardCorner.NORTH) == null || mapArray[x][y - 1].getCardCorner(CardCorner.SOUTH) == null) {
            throw new PersonalizedException.InvalidPlacementException();
        }

        return adiacentNumCard;
    }

    public Card getCard(int x, int y) { return mapArray[x][y]; }

    public Card[][] getMapArray() {
        return mapArray;
    }

    public PlayerScoreResource getPlayerScoreCard() {
        return playerScoreCard;
    }
}
