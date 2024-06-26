package it.polimi.codexnaturalis.view.TUI;

import it.polimi.codexnaturalis.view.VirtualModel.Hand.Hand;
import it.polimi.codexnaturalis.model.shop.card.Card;

public class PrintHandClass {

    public static void main(String[] args) {
    }

    public  static void printHand(Hand hand) {
        String Command;
        int i = 1;
        for (Card card : hand.getCards()) {
            System.out.println("card number: " + i);
            PrintCardClass.printCardHorizzontal(card);
            i++;
        }
        if (hand.getCards().isEmpty()) {
            System.out.println("No cards in hand");
        }
    }
}
