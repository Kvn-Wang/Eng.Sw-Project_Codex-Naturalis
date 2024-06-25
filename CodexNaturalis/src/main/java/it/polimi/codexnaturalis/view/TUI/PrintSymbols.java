package it.polimi.codexnaturalis.view.TUI;

import it.polimi.codexnaturalis.model.enumeration.ConditionResourceType;
import it.polimi.codexnaturalis.model.enumeration.ResourceType;
import it.polimi.codexnaturalis.model.shop.card.Card;

public class PrintSymbols {
    public static String[] convertMultipleResourceType(ResourceType[] resourceType, boolean isCard) {
        int len = resourceType.length;
        String[] c = new String[len];
        for (int i = 0; i < len; i++) {
            c[i] = convertResourceType(resourceType[i], isCard);
        }
        return c;
    }

    public static String convertResourceType(ResourceType resourceType, boolean isCard) {
        final String ANSI_RED = "\u001B[31m";
        final String ANSI_GREEN = "\u001B[32m";
        final String ANSI_BLUE = "\u001B[34m";
        final String ANSI_PURPLE = "\u001B[35m";
        final String ANSI_RESET = "\u001B[0m";

        switch (resourceType) {
            case UNASSIGNABLE -> {
                return "X";
            }
            case NONE -> {
                return " ";
            }
            case PLANT -> {
                return ANSI_GREEN+"█"+ANSI_RESET;
            }
            case ANIMAL -> {
                return ANSI_BLUE+"█"+ANSI_RESET;
            }
            case FUNGI -> {
                return ANSI_RED+"█"+ANSI_RESET;
            }
            case INSECT -> {
                return ANSI_PURPLE+"█"+ANSI_RESET;
            }
            case QUILL -> {
                return "Q";
            }
            case INKWELL -> {
                return "I";
            }
            case MANUSCRIPT -> {
                return "M";
            }
        }
        return null;
    }

    public static String convertConditionType(ConditionResourceType conditionResourceType, boolean isCard) {
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
                return "U+274C";
            }
        }
        return null;
    }

    public static String convertColor(ResourceType resourceType) {
        final String ANSI_RED = "\u001B[31m";
        final String ANSI_GREEN = "\u001B[32m";
        final String ANSI_BLUE = "\u001B[34m";
        final String ANSI_PURPLE = "\u001B[35m";

        switch (resourceType) {
            case PLANT -> {
                return ANSI_GREEN;
            }
            case ANIMAL -> {
                return ANSI_BLUE;
            }
            case FUNGI -> {
                return ANSI_RED;
            }
            case INSECT -> {
                return ANSI_PURPLE;
            }
        }

        return "";
    }
}
