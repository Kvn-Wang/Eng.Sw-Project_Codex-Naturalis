package it.polimi.codexnaturalis.view.TUI;

import it.polimi.codexnaturalis.model.enumeration.CardType;
import it.polimi.codexnaturalis.model.enumeration.ResourceType;
import it.polimi.codexnaturalis.model.shop.card.Card;
import it.polimi.codexnaturalis.model.shop.card.ResourceCard;
import it.polimi.codexnaturalis.model.shop.card.StarterCard;

public class PrintCardClass {
    boolean isNwCovered;
    boolean isNeCovered;
    boolean isSeCovered;
    boolean isSwCovered;
    static String [] TUICard;
    static Card card;
    static final int cardLen = 5;

    // ANSI Escape Codes for colors of the card
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_WHITE = "\u001B[97m";

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String colorCorner = ANSI_WHITE;
    private static final String colorCenter = ANSI_YELLOW;


    public PrintCardClass(Card card) {
        this.card = card;
        isNwCovered = false;
        isNeCovered = false;
        isSeCovered = false;
        isSwCovered = false;
        TUICard = new String[7];
    }

    public static void main(String[] args) {
        StarterCard starterCard = new StarterCard(81, ResourceType.NONE, ResourceType.NONE, ResourceType.PLANT, ResourceType.INSECT, new ResourceType[]{ResourceType.INSECT, ResourceType.ANIMAL}, ResourceType.FUNGI, ResourceType.ANIMAL, ResourceType.PLANT, ResourceType.INSECT);
        ResourceCard firstTestCard = new ResourceCard(1, ResourceType.FUNGI, ResourceType.UNASSIGNABLE, ResourceType.NONE, ResourceType.FUNGI, ResourceType.FUNGI, 0);
        printCard(firstTestCard, true);
        printCard(firstTestCard, false);
        printCard(starterCard, true);
        printCard(starterCard, false);
    }

    public static String[] createCard(Card card, boolean isFront) {
        String nw, ne, sw, se;
        String[] c;

        if (isFront) {
            nw = PrintSymbols.convertResourceType(card.getFrontNorthResource(), true);
            ne = PrintSymbols.convertResourceType(card.getFrontEastResource(), true);
            se = PrintSymbols.convertResourceType(card.getFrontSouthResource(), true);
            sw = PrintSymbols.convertResourceType(card.getFrontWestResource(), true);
            if (card.getCardType() == CardType.STARTER){
                c = PrintSymbols.convertMultipleResourceType(card.getBackCentralResources(), true);
            } else {c = PrintSymbols.convertMultipleResourceType(new ResourceType[]{ResourceType.NONE},true);
            }
        } else {
            nw = PrintSymbols.convertResourceType(card.getBackNorthResource(),true);
            ne = PrintSymbols.convertResourceType(card.getBackEastResource(),true);
            se = PrintSymbols.convertResourceType(card.getBackSouthResource(),true);
            sw = PrintSymbols.convertResourceType(card.getBackWestResource(),true);
            if (card.getCardType() != CardType.STARTER) {
                c = PrintSymbols.convertMultipleResourceType(card.getBackCentralResources(), true);
            }else {
                c = PrintSymbols.convertMultipleResourceType(new ResourceType[]{ResourceType.NONE}, true);
            }
        }

        TUICard = new String[5];

        nw = colorCorner + nw + ANSI_RESET;
        ne = colorCorner + ne + ANSI_RESET;
        sw = colorCorner + sw + ANSI_RESET;
        se = colorCorner + se + ANSI_RESET;

        String ANSI_COLOR = PrintSymbols.convertColor(card.getCardColor());

        TUICard[0] = ANSI_COLOR + "╔════╗" + ANSI_RESET;
        TUICard[1] = ANSI_COLOR + "║"+ ANSI_RESET + nw + "  " + ne + ANSI_COLOR+"║" + ANSI_RESET;
        if(c.length == 0) {
            TUICard[2] = ANSI_COLOR + "║" + "    " + "║" + ANSI_RESET;
        } else if(c.length == 1) {
            TUICard[2] = ANSI_COLOR + "║"+ colorCenter + " " + c[0] + "  " + ANSI_RESET + ANSI_COLOR+"║" + ANSI_RESET;
        } else if(c.length == 2) {
            TUICard[2] = ANSI_COLOR + "║"+ colorCenter + " " + c[0] + c[1] +" " + ANSI_RESET + ANSI_COLOR+"║" + ANSI_RESET;
        } else if(c.length == 3) {
            TUICard[2] = ANSI_COLOR + "║"+ colorCenter + c[2] + c[0] + c[1] + " " + ANSI_RESET + ANSI_COLOR+"║" + ANSI_RESET;
        }
        TUICard[3] = ANSI_COLOR + "║" + ANSI_RESET + sw + "  " + se + ANSI_COLOR + "║" + ANSI_RESET;
        TUICard[4] = ANSI_COLOR + "╚════╝" + ANSI_RESET;

        return TUICard;
    }

    public static void printCard(Card card, boolean isFront){
        createCard(card,isFront);
        for(int i = 0; i < TUICard.length; i++){
            System.out.println(TUICard[i]);
        }
    }

    public static void printCardHorizzontal(Card card){
        String[] front = createCard(card, true);
        String[] back = createCard(card, false);
        String repeatedSpaces = repeat(" ", cardLen);

        String [] printCard = new String[cardLen];

        for(int i = 0; i < TUICard.length; i++){
            printCard[i] = front[i] + repeatedSpaces + back[i];
        }

        System.out.println("Front:" + repeatedSpaces + "Back:");
        for(int i = 0; i < TUICard.length; i++){
            System.out.println(printCard[i]);
        }
    }

    private static String repeat(String str, int n) {
        if (str == null || n <= 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append(str);
        }
        return sb.toString();
    }
}

