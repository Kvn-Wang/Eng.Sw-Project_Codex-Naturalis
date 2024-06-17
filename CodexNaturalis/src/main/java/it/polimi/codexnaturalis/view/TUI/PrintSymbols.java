package it.polimi.codexnaturalis.view.TUI;

import it.polimi.codexnaturalis.model.enumeration.ConditionResourceType;
import it.polimi.codexnaturalis.model.enumeration.ResourceType;

public class PrintSymbols {

    public PrintSymbols() {
    }

    public static String[] convertMultipleResourceType(ResourceType[] resourceType, boolean isCard) {
        int len = resourceType.length;
        String[] c = new String[len];
        for (int i = 0; i < len; i++) {
            c[i] = convertResourceType(resourceType[i], isCard);
        }
        return c;
    }

    public static String convertResourceType(ResourceType resourceType, boolean isCard) {
        switch (resourceType) {
            case UNASSIGNABLE -> {
                return "X";
            }
            case NONE -> {
                return "";
            }
            case PLANT -> {
                if (isCard) {
                    return "\uD83C\uDF43";
                } else {
                    return "\uD83D\uDFE9";
                }
            }
            case ANIMAL -> {
                if(isCard) {
                    return "\uD83D\uDC3A";
                } else{
                    return "\uD83D\uDFE6";
                }
            }
            case FUNGI -> {
                if(isCard) {
                    return "\uD83C\uDF44";
                }else {
                    return "\uD83D\uDFE5";
                }
            }
            case INSECT -> {
                if(isCard) {
                    return "\uD83D\uDC1C";
                } else {
                    return "\uD83D\uDFEA";
                }
            }
            case QUILL -> {
                return "\uD83E\uDEB6";
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
}
