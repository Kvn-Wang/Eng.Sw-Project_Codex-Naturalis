package it.polimi.codexnaturalis.model.player;

import it.polimi.codexnaturalis.model.enumeration.CardCorner;
import it.polimi.codexnaturalis.model.enumeration.ResourceType;
import it.polimi.codexnaturalis.model.shop.card.Card;
import it.polimi.codexnaturalis.utils.PersonalizedException;
import it.polimi.codexnaturalis.utils.UtilCostantValue;
import java.util.ArrayList;

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
        }//cancellato il metodo per la starter card
    }

    //la chiamata a placeCard ritornerà
    //throw exception come valore speciale per dire che non vale piazzare la carta,
    // = 0 come valore per indicare che la carta è stata aggiunta senza aggiunta eventuali di punti (carte obbiettivo o carte risorsa front),
    // oppure > 0 per indicare che la carta piazzata deve aggiungere punti equivalente al valore di ritorno al punteggio del player
    // la carta è piazzabile se c'è una carta valida a fianco
    public int placeCard(int x, int y, Card card, boolean isCardBack) throws PersonalizedException.InvalidPlacementException {
        int neightbouringCard;
        ArrayList<ResourceType> tempListOfResources;
        int pointToAdd;

        if(checkValidityXY(x, y)) {
            //controlla le carte adiacenti per eventuali impossibilità per piazzare la carta
            neightbouringCard = checkValidPosition(x, y);
            if(neightbouringCard > 0){
                //funzione per controllare i requisiti per le carte obbiettivo
                if(card.checkPlaceableCardCondition(playerScoreCard)) {
                    //piazza la carta
                    card.setIsBack(isCardBack);
                    mapArray[x][y] = card;

                    //per controllare che risorse devo aggiungere
                    tempListOfResources = card.getCardResources();
                    for(ResourceType element : tempListOfResources) {
                        playerScoreCard.addScore(element);
                    }

                    //controllo di quali risorse vengono coperte dopo aver piazzato la carta
                    tempListOfResources = checkResourceCovered(x, y);
                    for(ResourceType element : tempListOfResources) {
                        playerScoreCard.substractScore(element);
                    }

                    //ritorna i punti da aggiungere a playerScore
                    pointToAdd = card.getCardPoints(playerScoreCard, neightbouringCard);

                    return pointToAdd;
                } else {
                    throw new PersonalizedException.InvalidPlacementException();
                }
            } else {
                throw new PersonalizedException.InvalidPlacementException();
            }
        } else {
            throw new PersonalizedException.InvalidPlacementException();
        }
    }

    // Controlla che le celle attorno almeno una sia "piena" e che l'angolo sia valido per piazzare una carta
    // se tutto va bene, ritorna il numero di carte adiacenti ad essa, altrimenti lancia una Exception
    private int checkValidPosition(int x, int y) throws PersonalizedException.InvalidPlacementException {
        int adiacentNumCard = 0;

        //controllo di adiacenza della carta facendo attenzione ai valori limite
        if(x == UtilCostantValue.lunghezzaMaxMappa - 1) {
            if(!(mapArray[x - 1][y] == null)) {
                adiacentNumCard++;
            }

            //controllo del corner
            if(mapArray[x - 1][y].getCardCorner(CardCorner.EAST) == null) {
                throw new PersonalizedException.InvalidPlacementException();
            }
        } else if(x == 0) {
            if(!(mapArray[x + 1][y] == null)) {
                adiacentNumCard++;
            }

            //controllo del corner
            if(mapArray[x + 1][y].getCardCorner(CardCorner.WEST) == null) {
                throw new PersonalizedException.InvalidPlacementException();
            }
        } else {
            if(!(mapArray[x + 1][y] == null)) {
                adiacentNumCard++;
            }
            if(!(mapArray[x - 1][y] == null)) {
                adiacentNumCard++;
            }

            //controllo del corner
            if(mapArray[x + 1][y].getCardCorner(CardCorner.EAST) == null || mapArray[x + 1][y].getCardCorner(CardCorner.WEST) == null) {
                throw new PersonalizedException.InvalidPlacementException();
            }
        }

        if(y == UtilCostantValue.lunghezzaMaxMappa - 1) {
            if(!(mapArray[x][y - 1] == null)) {
                adiacentNumCard++;
            }

            //controllo del corner
            if(mapArray[x][y - 1].getCardCorner(CardCorner.SOUTH) == null) {
                throw new PersonalizedException.InvalidPlacementException();
            }
        } else if(y == 0) {
            if(!(mapArray[x][y + 1] == null)) {
                adiacentNumCard++;
            }

            //controllo del corner
            if(mapArray[x][y + 1].getCardCorner(CardCorner.NORTH) == null) {
                throw new PersonalizedException.InvalidPlacementException();
            }
        } else {
            if(!(mapArray[x][y + 1] == null)) {
                adiacentNumCard++;
            }
            if(!(mapArray[x][y - 1] == null)) {
                adiacentNumCard++;
            }

            //controllo del corner
            if(mapArray[x][y - 1].getCardCorner(CardCorner.SOUTH) == null || mapArray[x][y + 1].getCardCorner(CardCorner.NORTH) == null) {
                throw new PersonalizedException.InvalidPlacementException();
            }
        }

        if(adiacentNumCard == 0) {
            throw new PersonalizedException.InvalidPlacementException();
        }

        return adiacentNumCard;
    }

    private ArrayList<ResourceType> checkResourceCovered(int x, int y) {
        ArrayList<ResourceType> coveredResource = null;
        ResourceType temp;

        temp = mapArray[x + 1][y].getCardCorner(CardCorner.WEST);
        if(temp != null && temp != ResourceType.NONE) {
            coveredResource.add(temp);
        }

        temp = mapArray[x - 1][y].getCardCorner(CardCorner.EAST);
        if(temp != null && temp != ResourceType.NONE) {
            coveredResource.add(temp);
        }

        temp = mapArray[x][y + 1].getCardCorner(CardCorner.NORTH);
        if(temp != null && temp != ResourceType.NONE) {
            coveredResource.add(temp);
        }

        temp = mapArray[x][y - 1].getCardCorner(CardCorner.SOUTH);
        if(temp != null && temp != ResourceType.NONE) {
            coveredResource.add(temp);
        }

        return coveredResource;
    }

    public boolean checkValidityXY(int x, int y) {
        if(x < 0 || y < 0 || x > UtilCostantValue.lunghezzaMaxMappa || y > UtilCostantValue.lunghezzaMaxMappa) {
            return false;
        } else {
            return true;
        }
    }

    public Card[][] getMapArray() {
        return mapArray;
    }
}
