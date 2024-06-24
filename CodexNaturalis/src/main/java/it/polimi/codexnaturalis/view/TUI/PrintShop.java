package it.polimi.codexnaturalis.view.TUI;

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
        System.out.println("2)");
        PrintCardClass.printCardHorizzontal(resourceVisibleCard2);

        System.out.println(ANSI_BLUE + "Obj card Available: " + ANSI_RESET);
        System.out.println("topDeckCard:");
        PrintCardClass.printCard(objTopDeckCard, false);
        System.out.println("1)");
        PrintCardClass.printCardHorizzontal(objVisibleCard1);
        System.out.println("2)");
        PrintCardClass.printCardHorizzontal(objVisibleCard2);
    }
}
