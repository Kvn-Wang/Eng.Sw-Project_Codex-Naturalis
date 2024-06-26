package it.polimi.codexnaturalis.view.TUI;

import it.polimi.codexnaturalis.model.enumeration.ConditionResourceType;
import it.polimi.codexnaturalis.model.enumeration.ResourceType;
import it.polimi.codexnaturalis.model.shop.card.ObjectiveCard;
import it.polimi.codexnaturalis.model.shop.card.ResourceCard;
import it.polimi.codexnaturalis.model.shop.card.StarterCard;
import it.polimi.codexnaturalis.view.VirtualModel.Hand.Hand;
import it.polimi.codexnaturalis.model.shop.card.Card;

public class PrintHandClass {

    public static void main(String[] args) {
        Hand hand = new Hand();
        ObjectiveCard thirdTestCard = new ObjectiveCard(41, ResourceType.UNASSIGNABLE, ResourceType.QUILL, ResourceType.NONE, ResourceType.NONE, ResourceType.FUNGI, ConditionResourceType.QUILL, 1, new ResourceType[]{ResourceType.FUNGI, ResourceType.FUNGI, ResourceType.ANIMAL});
        ResourceCard firstTestCard = new ResourceCard(1, ResourceType.FUNGI, ResourceType.UNASSIGNABLE, ResourceType.NONE, ResourceType.FUNGI, ResourceType.FUNGI, 0);
        hand.addCard(firstTestCard);
        hand.addCard(thirdTestCard);
        printHand(hand);
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
