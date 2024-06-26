package it.polimi.codexnaturalis.view.TUI;

import it.polimi.codexnaturalis.model.enumeration.ConditionResourceType;
import it.polimi.codexnaturalis.model.enumeration.ResourceType;
import it.polimi.codexnaturalis.view.VirtualModel.Hand.Hand;
import it.polimi.codexnaturalis.model.shop.card.Card;

public class PrintHandClass {

    public static void main(String[] args) {
    }

    public  static void printHand(Hand hand) {
        String Command;
        int i = 1;
        for (Card card : hand.getCards()) {
            System.out.println("card number: " + i +"  card type: " + card.getCardType());
            PrintCardClass.printCardHorizzontal(card);

            // print upper front card
            ConditionResourceType condition = card.getCondition();
            int points = card.getFrontalNumber();
            if(!condition.equals(ConditionResourceType.NONE)) { // when it's objective card
                String conditionString = PrintSymbols.convertConditionType(condition);
                System.out.println("Points: " + points + ", Condition: " + conditionString);
            } else if(points != 0) { // when a card is resource and gives point
                System.out.println("Points: " + points);
            }

            // print bottom front card
            ResourceType[] bottomCondition = card.getPlaceableCardResources();
            if(bottomCondition != null) { // if the card has conditions
                String[] bottomString = PrintSymbols.convertMultipleResourceType(bottomCondition, false);

                System.out.print("Condition to place: ");
                for(int j = 0; j < bottomString.length; j++) {
                    System.out.print(" ");
                    System.out.print(bottomString[j]);
                }
                System.out.print("\n");
            }

            i++;
        }
        if (hand.getCards().isEmpty()) {
            System.out.println("No cards in hand");
        }
    }
}
