package it.polimi.codexnaturalis.view.TUI;

import it.polimi.codexnaturalis.model.enumeration.ConditionResourceType;
import it.polimi.codexnaturalis.model.enumeration.ResourceType;
import it.polimi.codexnaturalis.model.shop.card.Card;
import it.polimi.codexnaturalis.model.shop.card.StarterCard;

public class PrintCardClass {
    public static void main(String[] args) {
        StarterCard starterCard = new StarterCard(81, ResourceType.NONE, ResourceType.NONE, ResourceType.PLANT, ResourceType.INSECT, new ResourceType[]{ResourceType.INSECT}, ResourceType.FUNGI, ResourceType.ANIMAL, ResourceType.PLANT, ResourceType.INSECT);

        printCard(starterCard, true);
        printCard(starterCard, false);
    }

    public static void printCard(Card card, boolean isFront) {
        String nw, ne, sw, se;
        String[] c;
        int punti;
        String conditionResource;
        String[] conditionPlaceableCardResource;

        if (isFront) {
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

        nw = formatCorner(5, nw);
        ne = formatCorner(5, ne);
        sw = formatCorner(5, sw);
        se = formatCorner(5, se);

        System.out.println("╔═══════╗");
        if (punti == 0 && conditionResource.equals("")) {
            System.out.println(nw + "   " + ne);
        } else if (punti != 0 && conditionResource.equals("")) {
            System.out.println(nw + " " + punti + " " + ne);
        } else {
            System.out.println(nw + " " + punti + " | " + conditionResource + " " + ne);
        }
        System.out.println("╠═╩═════╩═╣");
        System.out.println("║  " + (c.length > 1 ? c[1] : " ") + "  ║");
        System.out.println("║  " + (c.length > 0 ? c[0] : " ") + "  ║");
        System.out.println("║  " + (c.length > 2 ? c[2] : " ") + "  ║");
        System.out.println("╠═╦═════╦═╣");
        String conditionResourcesStr = conditionPlaceableCardResource != null ? String.join(", ", conditionPlaceableCardResource) : "";
        System.out.println(sw + " " + conditionResourcesStr + " " + se);
        System.out.println("╚═╩═════╩═╝");
    }

    private static String[] convertMultipleResourceType(ResourceType[] resourceType) {
        int len = resourceType.length;
        String[] c = new String[len];
        for (int i = 0; i < len; i++) {
            c[i] = convertResourceType(resourceType[i]);
        }
        return c;
    }

    private static String convertResourceType(ResourceType resourceType) {
        switch (resourceType) {
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
        switch (conditionResourceType) {
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
                return "\u2588";
            }
        }
        return null;
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

