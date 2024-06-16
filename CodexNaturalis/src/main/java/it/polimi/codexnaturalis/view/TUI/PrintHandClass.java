package it.polimi.codexnaturalis.view.TUI;

import it.polimi.codexnaturalis.model.player.Hand;
import it.polimi.codexnaturalis.model.shop.card.Card;
import it.polimi.codexnaturalis.view.VirtualModel.ClientContainer;
import it.polimi.codexnaturalis.view.VirtualModel.ClientContainerController;

public class PrintHandClass {

    public  static void printHand(Hand hand, ClientContainerController clientContainer) {
        String Command;
        int i = 0;
        Hand printHand = clientContainer.getHand();
        for (Card card : printHand.getCards()) {
            System.out.println("card number: " + i);
            System.out.println("front:");
            PrintCardClass.printCard(card, true);
            System.out.println("back:");
            PrintCardClass.printCard(card, false);
        }
        if (printHand.getCards().isEmpty()) {
            System.out.println("No cards in hand");
        }
    }
}
