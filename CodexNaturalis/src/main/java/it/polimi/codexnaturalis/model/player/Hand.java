package it.polimi.codexnaturalis.model.player;

import it.polimi.codexnaturalis.model.shop.card.Card;
import it.polimi.codexnaturalis.utils.PersonalizedException;

public class Hand {
    private Card[] cards = new Card[3];

    public void addCard(Card drawnCard) throws PersonalizedException.InvalidAddCardException {
        boolean isFull=true;
        for(int i = 0; i < cards.length; i++) {
            if (cards[i] == null) {
                cards[i] = drawnCard;
                isFull = false;
                break;
            }
        }
        if(isFull){
            throw new PersonalizedException.InvalidAddCardException();
        }
    }

    public Card popCard(int numCard) {
        Card suppCard;
        suppCard = cards[numCard];
        cards[numCard] = null;

        return suppCard;
    }

}
