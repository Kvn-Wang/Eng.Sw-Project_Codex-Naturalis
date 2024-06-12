package it.polimi.codexnaturalis.view.TUI;

import it.polimi.codexnaturalis.model.enumeration.ConditionResourceType;
import it.polimi.codexnaturalis.model.enumeration.ResourceType;

public class PrintSymbols {

    public PrintSymbols() {
    }

    public static String[] convertMultipleResourceType(ResourceType[] resourceType) {
        int len = resourceType.length;
        String[] c = new String[len];
        for (int i = 0; i < len; i++) {
            c[i] = convertResourceType(resourceType[i]);
        }
        return c;
    }

    public static String convertResourceType(ResourceType resourceType) {
        switch (resourceType) {
            case UNASSIGNABLE -> {
                return "X";
            }
            case NONE -> {
                return "";
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

    public static String convertConditionType(ConditionResourceType conditionResourceType) {
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
}
