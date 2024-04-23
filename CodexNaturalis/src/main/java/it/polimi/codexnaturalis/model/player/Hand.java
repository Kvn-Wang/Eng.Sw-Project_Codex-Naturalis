package it.polimi.codexnaturalis.model.player;

import it.polimi.codexnaturalis.model.shop.card.Card;
import it.polimi.codexnaturalis.utils.PersonalizedException;

public class Hand {
    private Card[] cards;

    public void addCard(Card drawnCard) throws PersonalizedException.InvalidAddCardException {
        boolean isFull=true;
        for(Card c: cards) {
            if (c.equals(null)) {
                c = drawnCard;
                isFull = false;
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
