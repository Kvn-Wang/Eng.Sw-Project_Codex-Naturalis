package it.polimi.codexnaturalis.view.TUI;

import it.polimi.codexnaturalis.model.enumeration.ResourceType;
import it.polimi.codexnaturalis.model.player.GamePlayerMap;
import it.polimi.codexnaturalis.model.player.PlayerScoreResource;
import it.polimi.codexnaturalis.model.shop.card.Card;
import it.polimi.codexnaturalis.model.shop.card.ResourceCard;
import it.polimi.codexnaturalis.model.shop.card.StarterCard;
import it.polimi.codexnaturalis.utils.PersonalizedException;
import it.polimi.codexnaturalis.utils.UtilCostantValue;

// numero di spazio da spostare formula Ncarte -1 * 23 +15
public class PrintMapClass {

    String[] TUIMap;

    public PrintMapClass() {
    }

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
            map.placeCard((UtilCostantValue.lunghezzaMaxMappa/2) -1, UtilCostantValue.lunghezzaMaxMappa/2, firstTestCard);
        } catch (PersonalizedException.InvalidPlacementException e) {
            throw new RuntimeException(e);
        } catch (PersonalizedException.InvalidPlaceCardRequirementException e) {
            throw new RuntimeException(e);
        }

        printMap(map);
    }

    public static void printMap(GamePlayerMap map){
        int[] leftMostPrintableCard;
        int[] firstPrintableCard;
        Card[][] TUIMap = map.getMapArray();
        leftMostPrintableCard= leftMostPrintableCardPos(TUIMap);
        firstPrintableCard = firstPrintableCardPos(TUIMap);
        System.out.println(firstPrintableCard[0] + " " + firstPrintableCard[1]);
        PrintCardClass.printCard(TUIMap[firstPrintableCard[0]][firstPrintableCard[1]], true);
        PrintCardClass.printCard(TUIMap[UtilCostantValue.lunghezzaMaxMappa/2][UtilCostantValue.lunghezzaMaxMappa/2], true);
    }

    public static int[] leftMostPrintableCardPos(Card[][] mapArray){
        int i = 0, j = 0, maxRow = 0, maxCol = 0, minRow = 0, minCol = 0;
        boolean isGrowing = true;
        Card card = null;
        while(card == null && maxRow <= mapArray.length && maxCol <= mapArray[0].length && minRow < mapArray[0].length && minCol < mapArray[0].length){
            card = mapArray[i][j];
            if(card == null) {
                if (i == maxRow) {
                    if (maxRow == mapArray.length - 1) {
                        minRow++;
                    } else {
                        maxRow++;
                    }
                    i = minRow;
                } else {
                    i++;
                }
                if (j == minCol) {
                    if (maxCol == mapArray[0].length - 1 || isGrowing == false) {
                        isGrowing = false;
                        minCol++;
                    } else {
                        maxCol++;
                    }
                    j = maxCol;
                } else {
                    j--;
                }
            }
        }
        System.out.println(i + "d" + j);
        return new int[]{i,j};
    }

    public static int[] firstPrintableCardPos(Card[][] mapArray){
        int i = 0, j = mapArray.length-1, maxRow = 0 , minRow = 0, minCol = mapArray.length-1, maxCol = mapArray[0].length-1;
        boolean isGrowing = true;
        Card card = null;
        while(card == null && maxRow >= 0 && minCol >= 0 && minRow < mapArray.length && minCol < mapArray[0].length){
            card = mapArray[i][j];
            if(card == null) {
                if (i == maxRow) {
                    if (maxRow == mapArray.length - 1) {
                        minRow++;
                    } else {
                        maxRow++;
                    }
                    i = minRow;
                } else {
                    i++;
                }
                if (j == maxCol) {
                    if (minCol == 0 || isGrowing == false) {
                        j = minCol;
                        isGrowing = false;
                        maxCol--;
                    } else {
                        minCol--;
                        j = minCol;
                    }
                } else {
                    j++;
                }
            }
        }
        System.out.println(i+" "+ j);
        return new int[]{i,j};
    }

    public static Card[][] mapRearranger(Card[][] mapArray){
        return null;
    }
}
