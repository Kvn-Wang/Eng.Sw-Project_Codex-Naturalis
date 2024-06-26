package it.polimi.codexnaturalis.view.TUI;

import it.polimi.codexnaturalis.model.enumeration.ConditionResourceType;
import it.polimi.codexnaturalis.model.enumeration.ResourceType;
import it.polimi.codexnaturalis.model.shop.card.Card;
import it.polimi.codexnaturalis.view.VirtualModel.ClientContainer;

public class PrintShop {
    private static final String ANSI_RESET = "\033[0m";
    private static final String ANSI_BLUE = "\033[34m";
    public static void printShop(ClientContainer clientContainer) {
        Card resourceTopDeckCard = clientContainer.getTopDeckResourceCardShop();
        Card resourceVisibleCard1 = clientContainer.getVisibleResourceCardShop()[0];
        Card resourceVisibleCard2 = clientContainer.getVisibleResourceCardShop()[1];

        Card objTopDeckCard = clientContainer.getTopDeckObjCardShop();
        Card objVisibleCard1 = clientContainer.getVisibleObjectiveCardShop()[0];
        Card objVisibleCard2 = clientContainer.getVisibleObjectiveCardShop()[1];

        System.out.println(ANSI_BLUE + "Resource card Available: " + ANSI_RESET);
        System.out.println("topDeckCard:");
        PrintCardClass.printCard(resourceTopDeckCard, false);

        System.out.println("1)");
        PrintCardClass.printCardHorizzontal(resourceVisibleCard1);
        printConditionsCard(resourceVisibleCard1);

        System.out.println("2)");
        PrintCardClass.printCardHorizzontal(resourceVisibleCard2);
        printConditionsCard(resourceVisibleCard2);

        System.out.println(ANSI_BLUE + "Obj card Available: " + ANSI_RESET);
        System.out.println("topDeckCard:");
        PrintCardClass.printCard(objTopDeckCard, false);

        System.out.println("1)");
        PrintCardClass.printCardHorizzontal(objVisibleCard1);
        printConditionsCard(objVisibleCard1);

        System.out.println("2)");
        PrintCardClass.printCardHorizzontal(objVisibleCard2);
        printConditionsCard(objVisibleCard2);
    }

    private static void printConditionsCard(Card card) {
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
    }
}
