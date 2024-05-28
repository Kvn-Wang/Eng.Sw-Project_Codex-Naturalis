package it.polimi.codexnaturalis.view.TUI;

import it.polimi.codexnaturalis.model.enumeration.ConditionResourceType;
import it.polimi.codexnaturalis.model.enumeration.ResourceType;
import it.polimi.codexnaturalis.model.shop.card.Card;

public class PrintCardClass {
    public static void printCard(Card card, boolean isFront) {
        String nw, ne, sw, se;
        String[] c = new String[3];
        int punti;
        //per le carte obbiettivo
        String conditionResource;
        String[] conditionPlaceableCardResource = new String[5];

        if(isFront) {
            nw = convertResourceType(card.getFrontNorthResource());
            ne = convertResourceType(card.getFrontEastResource());
            sw = convertResourceType(card.getFrontSouthResource());
            se = convertResourceType(card.getFrontWestResource());
            c = convertMultipleResourceType(new ResourceType[]{ResourceType.NONE});
            punti = card.getFrontalNumber();
            conditionResource = convertConditionType(card.getCondition());
            conditionPlaceableCardResource = convertMultipleResourceType(card.getPlaceableCardResources());
        } else {
            nw = convertResourceType(card.getBackNorthResource());
            ne = convertResourceType(card.getBackEastResource());
            sw = convertResourceType(card.getBackSouthResource());
            se = convertResourceType(card.getBackWestResource());
            c = convertMultipleResourceType(card.getBackCentralResources());
            punti = 0;
            conditionResource = "";
            conditionPlaceableCardResource = null;
        }

        // Stampa della carta con i valori specificati
        System.out.println("+-----+");
        System.out.println("| " + nw + " "+ (punti != 0 ? punti : "") +"|"+ conditionResource + "  " + ne + " |");
        System.out.println("|  " + c[1] + "  |");
        System.out.println("|  " + c[0] + "  |");
        System.out.println("|  " + c[2] + "  |");
        System.out.println("| " + sw + " " + (conditionPlaceableCardResource != null ? conditionPlaceableCardResource : "") + "  " + se + " |");
        System.out.println("+-----+");
    }

    private static String[] convertMultipleResourceType(ResourceType[] resourceType) {
        int len;
        String[] c;

        len = resourceType.length;
        c = new String[len];
        for(int i = 0; i < len; i++) {
            c[i] = convertResourceType(resourceType[i]);
        }

        return c;
    }

    private static String convertResourceType(ResourceType resourceType) {
        switch(resourceType) {
            case UNASSIGNABLE -> {
                return "X";
            }
            case NONE -> {
                return " ";
            }
            case PLANT -> {
                return "\uD83C\uDF43";
            }
            case ANIMAL -> {
                return "\uD83D\uDC3E";
            }
            case FUNGI -> {
                return "\uD83C\uDF44";
            }
            case INSECT -> {
                return "\uD83D\uDC1E";
            }
            case QUILL -> {
                return "✒";
            }
            case INKWELL -> {
                return "⚱";
            }
            case MANUSCRIPT -> {
                return "\uD83D\uDCDC";
            }
        }
        return null;
    }

    private static String convertConditionType(ConditionResourceType conditionResourceType) {
        switch(conditionResourceType) {
            case NONE -> {
                return "";
            }
            case INKWELL -> {
                return "⚱";
            }
            case MANUSCRIPT -> {
                return "\uD83D\uDCDC";
            }
            case QUILL -> {
                return "✒";
            }
            case OCCUPIEDSPACE -> {
                return "\\u2588";
            }
        }
        return null;
    }
}
