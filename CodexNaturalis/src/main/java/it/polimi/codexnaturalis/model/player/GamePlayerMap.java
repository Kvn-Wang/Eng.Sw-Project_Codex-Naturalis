package it.polimi.codexnaturalis.model.player;

import it.polimi.codexnaturalis.model.enumeration.CardCorner;
import it.polimi.codexnaturalis.model.enumeration.CardType;
import it.polimi.codexnaturalis.model.enumeration.ResourceType;
import it.polimi.codexnaturalis.model.shop.card.Card;
import it.polimi.codexnaturalis.utils.PersonalizedException;
import it.polimi.codexnaturalis.utils.UtilCostantValue;

import java.util.ArrayList;

/**
 * The type Game player map.
 */
public class GamePlayerMap {

    private Card[][] mapArray;

    private PlayerScoreResource playerScoreCard;

    /**
     * Gets player score card.
     *
     * @return the player score card
     */
    public PlayerScoreResource getPlayerScoreCard() {
        return playerScoreCard;
    }

    /**
     * Instantiates a new Game player map.
     *
     * @param playerScoreResource the player score resource
     */
    public GamePlayerMap(PlayerScoreResource playerScoreResource) {
        this.playerScoreCard = playerScoreResource;
        this.mapArray = new Card[UtilCostantValue.lunghezzaMaxMappa][UtilCostantValue.lunghezzaMaxMappa];

        //inizializzazione valori della mappa
        for(int i = 0; i < UtilCostantValue.lunghezzaMaxMappa; i++) {
            for(int j = 0; j < UtilCostantValue.lunghezzaMaxMappa; j++) {
                mapArray[i][j] = null;
            }
        }//cancellato il metodo per la starter card
    }

    /**
     * Place card
     * the call to placeCard is returning
     * the card is placeable if there is a valid one in the side
     *
     * @param x    the x
     * @param y    the y
     * @param card the card
     * @return the int
     * @throws InvalidPlacementException            the card cannot be placed (= 0 come valore per indicare che la carta è stata aggiunta senza aggiunta eventuali di punti (carte obbiettivo o carte risorsa front),
     * @throws InvalidPlaceCardRequirementException the invalid place card requirement exception (oppure > 0 per indicare che la carta piazzata deve aggiungere punti equivalente al valore di ritorno al punteggio del player)
     */
    public int placeCard(int x, int y, Card card) throws PersonalizedException.InvalidPlacementException, PersonalizedException.InvalidPlaceCardRequirementException {
        int neightbouringCard;
        ArrayList<ResourceType> tempListOfResources;
        int pointToAdd;

        if(checkValidityXY(x, y)) {
            //controlla le carte adiacenti per eventuali impossibilità per piazzare la carta
            neightbouringCard = checkValidPosition(x, y);
            if(neightbouringCard > 0 || (mapArray[UtilCostantValue.lunghezzaMaxMappa/2][UtilCostantValue.lunghezzaMaxMappa/2]==null && card.getCardType() == CardType.STARTER)){
                //funzione per controllare i requisiti per le carte obbiettivo
                if(card.checkPlaceableCardCondition(playerScoreCard) || card.getIsBack()) {
                    //piazza la carta
                    card.setIsBack(card.getIsBack());
                    mapArray[x][y] = card;

                    //per controllare che risorse devo aggiungere
                    tempListOfResources = card.getCardResources();
                    for(ResourceType element : tempListOfResources) {
                        playerScoreCard.addScore(element);
                    }

                    //controllo di quali risorse vengono coperte dopo aver piazzato la carta
                    tempListOfResources = checkResourceCovered(x, y);
                    if(!tempListOfResources.isEmpty()) {
                        if(tempListOfResources.contains(ResourceType.UNASSIGNABLE)) {
                            throw new PersonalizedException.InvalidPlacementException();
                        } else {
                            for (ResourceType element : tempListOfResources) {
                                playerScoreCard.substractScore(element);
                            }
                        }

                    }


                    //ritorna i punti da aggiungere a playerScore
                    pointToAdd = card.getCardPoints(playerScoreCard, neightbouringCard);
                    return pointToAdd;
                } else {
                    throw new PersonalizedException.InvalidPlaceCardRequirementException();
                }
            } else {
                throw new PersonalizedException.InvalidPlacementException();
            }
        } else {
            throw new PersonalizedException.InvalidPlacementException();
        }
    }

    /**
     * Get validity boolean.
     *
     * @param x the x
     * @param y the y
     * @return the boolean
     */
    public boolean getValidity(int x, int y){
        boolean validity;
        validity = checkValidityXY(x,y);
        return validity;
    }

    // Controlla che le celle attorno almeno una sia "piena" e che l'angolo sia valido per piazzare una carta
    // se tutto va bene, ritorna il numero di carte adiacenti ad essa, altrimenti lancia una Exception
    private int checkValidPosition(int x, int y) throws PersonalizedException.InvalidPlacementException {
        int adiacentNumCard = 0;

        //controllo di adiacenza della carta facendo attenzione ai valori limite
        if(x == UtilCostantValue.lunghezzaMaxMappa - 1) {
            if(!(mapArray[x - 1][y] == null)) {
                //controllo corner
                if(mapArray[x - 1][y].getCardCorner(CardCorner.SOUTH) == null) {
                    throw new PersonalizedException.InvalidPlacementException();
                }
                adiacentNumCard++;
            }
        } else if(x == 0) {
            if(!(mapArray[x + 1][y] == null)) {
                //controllo del corner
                if(mapArray[x + 1][y].getCardCorner(CardCorner.NORTH) == null) {
                    throw new PersonalizedException.InvalidPlacementException();
                }
                adiacentNumCard++;
            }
        } else {
            if(!(mapArray[x + 1][y] == null)) {
                if(mapArray[x + 1][y].getCardCorner(CardCorner.NORTH) == null) {
                    throw new PersonalizedException.InvalidPlacementException();
                }
                adiacentNumCard++;
            }
            if(!(mapArray[x - 1][y] == null)) {
                if(mapArray[x - 1][y].getCardCorner(CardCorner.SOUTH) == null) {
                    throw new PersonalizedException.InvalidPlacementException();
                }
                adiacentNumCard++;
            }
        }

        if(y == UtilCostantValue.lunghezzaMaxMappa - 1) {
            if(!(mapArray[x][y - 1] == null)) {
                //controllo del corner
                if(mapArray[x][y - 1].getCardCorner(CardCorner.EAST) == null) {
                    throw new PersonalizedException.InvalidPlacementException();
                }
                adiacentNumCard++;
            }
        } else if(y == 0) {
            if(!(mapArray[x][y + 1] == null)) {
                //controllo del corner
                if(mapArray[x][y + 1].getCardCorner(CardCorner.WEST) == null) {
                    throw new PersonalizedException.InvalidPlacementException();
                }
                adiacentNumCard++;
            }
        } else {
            if(!(mapArray[x][y + 1] == null)) {
                //controllo del corner
                if(mapArray[x][y + 1].getCardCorner(CardCorner.WEST) == null) {
                    throw new PersonalizedException.InvalidPlacementException();
                }
                adiacentNumCard++;
            }
            if(!(mapArray[x][y - 1] == null)) {
                //controllo del corner
                if(mapArray[x][y - 1].getCardCorner(CardCorner.EAST) == null) {
                    throw new PersonalizedException.InvalidPlacementException();
                }
                adiacentNumCard++;
            }
        }

        if(adiacentNumCard == 0 && mapArray[UtilCostantValue.lunghezzaMaxMappa/2][UtilCostantValue.lunghezzaMaxMappa/2]!=null) {
            throw new PersonalizedException.InvalidPlacementException();
        }
        return adiacentNumCard;
    }

    public int getCheckValidPosition(int x, int y){
        int adiacentNumCard = -1;
        if(checkValidityXY(x,y)){
            try {
                adiacentNumCard = checkValidPosition(x,y);
            } catch (PersonalizedException.InvalidPlacementException e) {
                return adiacentNumCard;
            }
        }
        return adiacentNumCard;
    }
    
    private ArrayList<ResourceType> checkResourceCovered(int x, int y) {
        ArrayList<ResourceType> coveredResource = new ArrayList<ResourceType>();
        ResourceType temp;

        if(x < UtilCostantValue.lunghezzaMaxMappa - 1 && mapArray[x + 1][y] != null) {
            temp = mapArray[x + 1][y].getCardCorner(CardCorner.NORTH);
            if (temp != null && temp != ResourceType.NONE) {
                coveredResource.add(temp);
            }
        }

        if(x > 0 && mapArray[x - 1][y]!=null && x > 0) {
            temp = mapArray[x - 1][y].getCardCorner(CardCorner.SOUTH);
            if (temp != null && temp != ResourceType.NONE) {
                coveredResource.add(temp);
            }
        }

        if(y < UtilCostantValue.lunghezzaMaxMappa - 1 && mapArray[x][y + 1]!=null) {
            temp = mapArray[x][y + 1].getCardCorner(CardCorner.EAST);
            if (temp != null && temp != ResourceType.NONE) {
                coveredResource.add(temp);
            }
        }

        if( y > 0 && mapArray[x][y - 1]!=null) {
            temp = mapArray[x][y - 1].getCardCorner(CardCorner.WEST);
            if (temp != null && temp != ResourceType.NONE) {
                coveredResource.add(temp);
            }
        }
        return coveredResource;
    }

    private boolean checkValidityXY(int x, int y) {
        if(x < 0 || y < 0 || x >= UtilCostantValue.lunghezzaMaxMappa || y >= UtilCostantValue.lunghezzaMaxMappa) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Get map array card [ ] [ ].
     *
     * @return the card [ ] [ ]
     */
    public Card[][] getMapArray() {
        return mapArray;
    }
}
