package it.polimi.codexnaturalis.view.TUI;

import it.polimi.codexnaturalis.model.enumeration.CardType;
import it.polimi.codexnaturalis.model.enumeration.ConditionResourceType;
import it.polimi.codexnaturalis.model.enumeration.ResourceType;
import it.polimi.codexnaturalis.model.shop.card.Card;
import it.polimi.codexnaturalis.model.shop.card.StarterCard;

public class PrintCardClass {
    public static void main(String[] args) {
        StarterCard starterCard = new StarterCard(81, ResourceType.NONE, ResourceType.NONE, ResourceType.PLANT, ResourceType.INSECT, new ResourceType[]{ResourceType.INSECT, ResourceType.ANIMAL, ResourceType.FUNGI}, ResourceType.FUNGI, ResourceType.ANIMAL, ResourceType.PLANT, ResourceType.INSECT);

        printCard(starterCard, true);
        printCard(starterCard, false);
    }

    public static void printCard(Card card, boolean isFront) {
        String nw, ne, sw, se, cardColor = "";
        String[] c;
        int punti;
        String conditionResource;
        String[] conditionPlaceableCardResource;

        if (isFront) {
            nw = PrintSymbols.convertResourceType(card.getFrontNorthResource(),true);
            ne = PrintSymbols.convertResourceType(card.getFrontEastResource(),true);
            se = PrintSymbols.convertResourceType(card.getFrontSouthResource(),true);
            sw = PrintSymbols.convertResourceType(card.getFrontWestResource(),true);
            c = PrintSymbols.convertMultipleResourceType(card.getBackCentralResources(),true);
            punti = card.getFrontalNumber();
            conditionResource = PrintSymbols.convertConditionType(card.getCondition(),true);
            conditionPlaceableCardResource = PrintSymbols.convertMultipleResourceType(card.getPlaceableCardResources(), true);
        } else {
            nw = PrintSymbols.convertResourceType(card.getBackNorthResource(),true);
            ne = PrintSymbols.convertResourceType(card.getBackEastResource(),true);
            se = PrintSymbols.convertResourceType(card.getBackSouthResource(),true);
            sw = PrintSymbols.convertResourceType(card.getBackWestResource(),true);
            c = PrintSymbols.convertMultipleResourceType(new ResourceType[]{ResourceType.NONE},true);
            punti = 0;
            conditionResource = "";
            conditionPlaceableCardResource = null;
        }
        if(card.getCardType() == CardType.STARTER){
            cardColor = "\uD83D\uDFE8"+"\u2009";
        }
        nw = formatCorner(6, nw);
        ne = formatCorner(6, ne);
        sw = formatCorner(6, sw);
        se = formatCorner(6, se);
        System.out.println("╔══════╦═══════╦══════╗");
        System.out.println(nw+"       "+ne);
        System.out.println("╠══════╝       ╚══════╣");
        System.out.println("║        " + (c.length > 1 ? c[1] : "   "+"\u2009") +  (c.length > 0 ? c[0] : "   "+"\u2009") +   (c.length > 2 ? c[2] : "   "+"\u2009") +"\u2009"+"      ║");
        System.out.println("╠══════╦═══════╦══════╣");
        System.out.println(sw+"  "+cardColor+"  "+se);
        System.out.println("╚══════╩═══════╩══════╝");
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

    private static String formatCorner(int totalLength, String cornerValue) {
        int valueLength = getVisualLength(cornerValue);
        int padding = (totalLength - valueLength) / 2;
        String paddingSpaces = " ".repeat(Math.max(0, padding));
        String formattedCorner = "║" + paddingSpaces + cornerValue + paddingSpaces;
        int remainingSpaces = totalLength - getVisualLength(formattedCorner);
        formattedCorner += " ".repeat(Math.max(0, remainingSpaces)) + "║";
        return formattedCorner;
    }
}

