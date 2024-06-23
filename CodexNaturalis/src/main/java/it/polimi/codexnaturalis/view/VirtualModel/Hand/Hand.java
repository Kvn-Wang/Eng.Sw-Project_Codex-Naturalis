package it.polimi.codexnaturalis.view.VirtualModel.Hand;

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

    public Card getCard(int numCard) {
        return cards.get(numCard);
    }

    public void addCard(Card drawnCard) {
        cards.add(drawnCard);
    }

    public Card popCard(int numCard) throws PersonalizedException.InvalidPopCardException {
        if(0 <= cards.size() && numCard < cards.size()) {
            return cards.remove(numCard);
            }
        else {
            throw new PersonalizedException.InvalidPopCardException();
        }
    }

    public boolean popCard(Card card) {
        /*System.out.println("Qualcuno ha giocato la carta: " + numCard);
        for(Card elem : cards) {
            System.out.println(elem);
        }*/

        return cards.remove(card);
    }
}
