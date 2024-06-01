package it.polimi.codexnaturalis.model.player;

import it.polimi.codexnaturalis.model.shop.card.Card;
import it.polimi.codexnaturalis.utils.PersonalizedException;

import java.util.ArrayList;

public class Hand {
    private ArrayList<Card> cards;

    public Hand() {
        cards = new ArrayList<>();
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void addCard(Card drawnCard) throws PersonalizedException.InvalidAddCardException {
        if(cards.size() <= 3) {
            cards.add(drawnCard);
        } else {
            throw new PersonalizedException.InvalidAddCardException();
        }
    }

    public Card popCard(int numCard) throws PersonalizedException.InvalidNumPopCardException, PersonalizedException.InvalidPopCardException {
        /*System.out.println("Qualcuno ha giocato la carta: " + numCard);
        for(Card elem : cards) {
            System.out.println(elem);
        }*/

        if(0 <= cards.size() && numCard < cards.size()) {
            return cards.remove(numCard);
            }
        else {
            throw new PersonalizedException.InvalidPopCardException();
        }
    }
}
