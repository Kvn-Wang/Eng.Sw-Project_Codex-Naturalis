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
        System.out.println("\uD83C\uDF44║");
        System.out.println("\u2004\u2004\u2004\u2004\u200A"+"║");
    }

    public static String[] createCard(Card card, boolean isFront) {
        String nw, ne, sw, se, cardColor = "", center = "";
        String[] c;
        boolean isCenterEmpty=false;
        int punti;
        String conditionResource;
        String[] conditionPlaceableCardResource;

        if (isFront) {
            nw = PrintSymbols.convertResourceType(card.getFrontNorthResource(), true);
            ne = PrintSymbols.convertResourceType(card.getFrontEastResource(), true);
            se = PrintSymbols.convertResourceType(card.getFrontSouthResource(), true);
            sw = PrintSymbols.convertResourceType(card.getFrontWestResource(), true);
            if (card.getCardType() == CardType.STARTER){
                c = PrintSymbols.convertMultipleResourceType(card.getBackCentralResources(), true);
            } else {c = PrintSymbols.convertMultipleResourceType(new ResourceType[]{ResourceType.NONE},true);
            isCenterEmpty = true;
            }

            punti = card.getFrontalNumber();
            conditionResource = PrintSymbols.convertConditionType(card.getCondition(),true);
            conditionPlaceableCardResource = PrintSymbols.convertMultipleResourceType(card.getPlaceableCardResources(), true);
        } else {
            nw = PrintSymbols.convertResourceType(card.getBackNorthResource(),true);
            ne = PrintSymbols.convertResourceType(card.getBackEastResource(),true);
            se = PrintSymbols.convertResourceType(card.getBackSouthResource(),true);
            sw = PrintSymbols.convertResourceType(card.getBackWestResource(),true);
            if (card.getCardType() != CardType.STARTER) {
                c = PrintSymbols.convertMultipleResourceType(card.getBackCentralResources(), true);
                isCenterEmpty = true;
            }else {
                c = PrintSymbols.convertMultipleResourceType(new ResourceType[]{ResourceType.NONE}, true);
            }
            punti = 0;
            conditionResource = "";
            conditionPlaceableCardResource = null;
        }
        if(card.getCardType() == CardType.STARTER){
            cardColor = "\uD83D\uDFE8"+"\u2004";
        }else{
            cardColor = PrintSymbols.convertResourceType(card.getCardColor(), false) + "\u2004";
        }

        if(isCenterEmpty==false) {
            center = formatCenter(c, card);
        }else{
            center = "       ";
        }
        TUICard = new String[7];
        nw = formatCorner(7, nw);
        ne = formatCorner(7, ne);
        sw = formatCorner(7, sw);
        se = formatCorner(7, se);
        TUICard[0] = "╔═══════╦═══════╦═══════╗";
        TUICard[1] = nw+"       "+ne;
        TUICard[2] = "╠═══════╝       ╚═══════╣";
        //TUICard[3] = "║       " + (c.length > 1 ? c[1] : "\u2004\u2004\u2004\u2004\u200A") +  (c.length > 0 ? c[0] : "\u2004\u2004\u2004\u2004\u200A") +   (c.length > 2 ? c[2] : "\u2004\u2004\u2004\u2004\u200A") +"\u2009"+"        ║";
        TUICard[3] = "║        " +center+"        ║";

        TUICard[4] = "╠═══════╦═══════╦═══════╣";
        TUICard[5] = sw+"  "+cardColor+"  "+se;
        TUICard[6] = "╚═══════╩═══════╩═══════╝";
        System.out.println(conditionResource);
        /*System.out.println("╔════╦═════╦════╗");
        if (punti == 0 && conditionResource.equals("")) {
            System.out.println(sw + "     " + nw);
        } else if (punti != 0 && conditionResource.equals("")) {
            System.out.println(nw + " " + punti + " " + ne);
        } else {
            System.out.println(nw + " " + punti + " | " + conditionResource + " " + ne);
        }
        System.out.println("╠════╩═════╩════╣");
        System.out.println("║  " + (c.length > 1 ? c[1] : " ") + "            ║");
        System.out.println("║  " + (c.length > 0 ? c[0] : "") + "            ║");
        System.out.println("║  " + (c.length > 2 ? c[2] : " ") + "            ║");
        System.out.println("╠════╦═════╦════╣");
        String conditionResourcesStr = conditionPlaceableCardResource != null ? String.join(", ", conditionPlaceableCardResource) : "";
        System.out.println(se + "    " + ne);
        System.out.println("╚════╩═════╩════╝");
        if(card.getCardType() == CardType.OBJECTIVE){
            System.out.println("necessary Condition: " + conditionResourcesStr);
        }
        System.out.println("╔══════╦═══════╦══════╗");
        System.out.println(nw+"       "+ne);
        System.out.println("╠══════╝       ╚══════╣");
        System.out.println("║        " + (c.length > 1 ? c[1] : "   "+"\u2009") +  (c.length > 0 ? c[0] : "   "+"\u2009") +   (c.length > 2 ? c[2] : "   "+"\u2009") +"\u2009"+"      ║");
        System.out.println("╠══════╦═══════╦══════╣");
        System.out.println(sw+"       "+se);
        System.out.println("╚══════╩═══════╩══════╝");*/
        return TUICard;
    }

    private static int getVisualLength(String str) {
        int length = 0;
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            // Aggiustamento per caratteri Unicode che occupano più spazio
            if (Character.isSupplementaryCodePoint(ch) || ch > 0xFFFF) {
                length += 2; // Supponiamo che occupi 2 spazi
            } else {
                length += 1;
            }
        }
        return length;
    }

    public static void printCard(Card card, boolean isFront){
        createCard(card,isFront);
        for(int i = 0;i < TUICard.length;i++){
            System.out.print(TUICard[i]+"\n");
        }
    }

    private static String formatCenter(String[] group, Card card){
        String centre = "";
        if(card.getCardType() == CardType.STARTER) {
            for (int i = 0; i < group.length; i++) {
                centre += group[i];
            }
            for (int i = 0; i < 3 - group.length; i++) {
                centre += "\u2004\u2004\u2004\u2004\u200A";
            }
            if(group[0].equals("")){
                centre += "\u2004\u2004\u2004\u2004\u200A";
            }
        }else {
            centre += "\u2004\u2004\u2004\u2004\u200A"+ PrintSymbols.convertResourceType(card.getCardColor(), true) +"\u2004\u2004\u2004\u2004\u200A";
        }
        return centre;
    }

    private static String formatCorner(int totalLength, String cornerValue) {
        int valueLength = getVisualLength(cornerValue);
        int padding = (totalLength - valueLength) / 2;
        String paddingSpaces = " ".repeat(Math.max(0, padding));
        String formattedCorner = "║" + paddingSpaces + cornerValue + paddingSpaces;
        int remainingSpaces = totalLength - getVisualLength(formattedCorner);
        if(totalLength%2 > 0){
            formattedCorner += " ".repeat(Math.max(0, remainingSpaces)) + "\u2004" + "║";
        }else {
            formattedCorner += " ".repeat(Math.max(0, remainingSpaces)) + "║";
        }
        return formattedCorner;
    }
}

