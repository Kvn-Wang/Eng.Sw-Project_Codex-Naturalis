package it.polimi.codexnaturalis.model.player;

import it.polimi.codexnaturalis.model.shop.card.Card;

public class Hand {
    private Card[] cards;

    public void addCard(Card drawnCard){
        for(Card c: cards)
            if(c.equals(null))
                c = drawnCard;
    }

    public Card popCard(int numCard) {
        Card suppCard;

        suppCard = cards[numCard];
        cards[numCard] = null;

        return suppCard;
    }
}
