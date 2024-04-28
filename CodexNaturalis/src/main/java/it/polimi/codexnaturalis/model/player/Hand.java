package it.polimi.codexnaturalis.model.player;

import it.polimi.codexnaturalis.model.shop.card.Card;
import it.polimi.codexnaturalis.utils.PersonalizedException;

public class Hand {
    private Card[] cards = new Card[3];

    public Hand() {
        for(int i=0; i<3; i++){
            cards[i] = null;
        }
    }

    public Card[] getCards() {
        return cards;
    }

    public void addCard(Card drawnCard) throws PersonalizedException.InvalidAddCardException {
        boolean isFull=true;
        for(int i= 0; i<3;i++) {
            if (cards[i]==null) {
                cards[i]=drawnCard;
                isFull = false;
                break;
            }
        }
        if(isFull){
            throw new PersonalizedException.InvalidAddCardException();
        }
    }

    public Card popCard(int numCard) throws PersonalizedException.InvalidNumPopCardException, PersonalizedException.InvalidPopCardException {
        if(0<=numCard && numCard<3) {
            Card suppCard;
            suppCard = cards[numCard];
            if(suppCard != null) {
                cards[numCard] = null;
                return suppCard;
            }else throw new PersonalizedException.InvalidPopCardException();
        }
        else throw new PersonalizedException.InvalidNumPopCardException();
    }

}
