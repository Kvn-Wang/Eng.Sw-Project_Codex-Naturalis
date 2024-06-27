package it.polimi.codexnaturalis.view.TUI;

import it.polimi.codexnaturalis.model.enumeration.ResourceType;
import it.polimi.codexnaturalis.model.player.GamePlayerMap;
import it.polimi.codexnaturalis.model.player.PlayerScoreResource;
import it.polimi.codexnaturalis.model.shop.card.ResourceCard;
import it.polimi.codexnaturalis.model.shop.card.StarterCard;
import it.polimi.codexnaturalis.utils.PersonalizedException;
import it.polimi.codexnaturalis.utils.UtilCostantValue;

public class test {
    private static String ANSI_COLOR = PrintSymbols.convertColor(ResourceType.NONE);
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String colorCenter = ANSI_YELLOW;
    public static void main(String[] args) {
        StarterCard starterCard = new StarterCard(81, ResourceType.NONE, ResourceType.NONE, ResourceType.PLANT, ResourceType.INSECT, new ResourceType[]{ResourceType.INSECT, ResourceType.ANIMAL, ResourceType.FUNGI}, ResourceType.FUNGI, ResourceType.ANIMAL, ResourceType.PLANT, ResourceType.INSECT);
        ResourceCard firstTestCard = new ResourceCard(1, ResourceType.FUNGI, ResourceType.UNASSIGNABLE, ResourceType.NONE, ResourceType.FUNGI, ResourceType.FUNGI, 0);
        PlayerScoreResource playerScoreResource = new PlayerScoreResource();
        GamePlayerMap map = new GamePlayerMap(playerScoreResource);
        try {
            map.placeCard(UtilCostantValue.lunghezzaMaxMappa/2, UtilCostantValue.lunghezzaMaxMappa/2, starterCard);
        } catch (PersonalizedException.InvalidPlacementException e) {
            throw new RuntimeException(e);
        } catch (PersonalizedException.InvalidPlaceCardRequirementException e) {
            throw new RuntimeException(e);
        }
        try {
            map.placeCard((UtilCostantValue.lunghezzaMaxMappa/2), UtilCostantValue.lunghezzaMaxMappa/2-1, firstTestCard);
        } catch (PersonalizedException.InvalidPlacementException e) {
            throw new RuntimeException(e);
        } catch (PersonalizedException.InvalidPlaceCardRequirementException e) {
            throw new RuntimeException(e);
        }
        //PrintMapClass.printYourMap(map.getMapArray());
        try {
            map.placeCard((UtilCostantValue.lunghezzaMaxMappa/2), UtilCostantValue.lunghezzaMaxMappa/2-2, firstTestCard);
        } catch (PersonalizedException.InvalidPlacementException e) {
            throw new RuntimeException(e);
        } catch (PersonalizedException.InvalidPlaceCardRequirementException e) {
            throw new RuntimeException(e);
        }
        //PrintMapClass.printYourMap(map.getMapArray());
        String TUICard = ANSI_COLOR + "║"+ colorCenter + " " + " " + "  " + ANSI_RESET + ANSI_COLOR+"║" + ANSI_RESET;
        System.out.println(TUICard);
        //String[] f = new String[]{"", "1", " ", ANSI_RESET, "2"};
        //System.out.println(f[0]+f[1]+f[2]+f[3]+f[4]);

    }
}
