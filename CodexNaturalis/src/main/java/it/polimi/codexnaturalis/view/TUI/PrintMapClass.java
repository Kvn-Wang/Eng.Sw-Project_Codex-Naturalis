package it.polimi.codexnaturalis.view.TUI;

import it.polimi.codexnaturalis.model.enumeration.ResourceType;
import it.polimi.codexnaturalis.model.player.GamePlayerMap;
import it.polimi.codexnaturalis.model.player.PlayerScoreResource;
import it.polimi.codexnaturalis.model.shop.card.Card;
import it.polimi.codexnaturalis.model.shop.card.ResourceCard;
import it.polimi.codexnaturalis.model.shop.card.StarterCard;
import it.polimi.codexnaturalis.utils.PersonalizedException;
import it.polimi.codexnaturalis.utils.UtilCostantValue;

import java.util.Arrays;

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
            map.placeCard((UtilCostantValue.lunghezzaMaxMappa/2), UtilCostantValue.lunghezzaMaxMappa/2-1, firstTestCard);
        } catch (PersonalizedException.InvalidPlacementException e) {
            throw new RuntimeException(e);
        } catch (PersonalizedException.InvalidPlaceCardRequirementException e) {
            throw new RuntimeException(e);
        }
        printMap(map.getMapArray());;
    }

    public static void printMap(Card[][] TUIMap){
        int[] leftMostPrintableCard;
        int[] firstPrintableCard;
        int[] lastPrintableCard;
        int counter;
        String space = " ".repeat(8);
        leftMostPrintableCard= leftMostPrintableCardPos(TUIMap);
        firstPrintableCard= firstPrintableCardPos(TUIMap);
        lastPrintableCard = lastPrintableCardPos(TUIMap);
        int lastRow = lastPrintableCard[0];
        int lastCol = lastPrintableCard[1];
        int firstPrintRow = firstPrintableCard[0];
        int frstPrintColumn = firstPrintableCard[1];
        int printRow = firstPrintableCard[0];
        int printColumn = firstPrintableCard[1];
        int[] lastCardInLine;
        while(firstPrintRow != lastRow || frstPrintColumn != lastCol){
            String[] TUICard = new String[7];
            lastCardInLine = lastCardInLinePos(TUIMap, frstPrintColumn, frstPrintColumn);
            while (printRow!=lastCardInLine[0]-1 && printColumn!=lastCardInLine[1]-1){
                if(TUIMap[printRow][printColumn] == null){
                    for(int i=0; i<TUICard.length; i++ ){
                        TUICard[i] =TUICard[i]+ space.repeat(3);
                    }
                }else{
                    String[] card = PrintCardClass.createCard(TUIMap[printRow][printColumn], true);
                    for(int i=0; i<TUICard.length; i++ ){
                        TUICard[i] =TUICard[i]+ card[i];
                    }
                }
                for(int i=0; i<TUICard.length; i++ ){
                    TUICard[i] =TUICard[i]+ space;
                }
                if(firstPrintRow < frstPrintColumn){
                    int[] newFirstCard = firstCardInLinePos(TUIMap, frstPrintColumn-1, firstPrintRow);
                    firstPrintRow =newFirstCard[0];
                    frstPrintColumn  =newFirstCard[1];
                }else{
                    int[] newFirstCard = firstCardInLinePos(TUIMap, frstPrintColumn, firstPrintRow+1);
                    firstPrintRow =newFirstCard[0];
                    frstPrintColumn  =newFirstCard[1];
                }
                printRow = firstPrintRow;
                printColumn = frstPrintColumn;
            }
            for(int i=0; i<TUICard.length; i++ ){
                System.out.println(TUICard[i]);
            }
            if(firstPrintRow < frstPrintColumn){
                int[] newFirstCard = firstCardInLinePos(TUIMap, frstPrintColumn-1, firstPrintRow);
                firstPrintRow =newFirstCard[0];
                frstPrintColumn  =newFirstCard[1];
            }else{
                int[] newFirstCard = firstCardInLinePos(TUIMap, frstPrintColumn, firstPrintRow+1);
                firstPrintRow =newFirstCard[0];
                frstPrintColumn  =newFirstCard[1];
            }
            printRow = firstPrintRow;
            printColumn = frstPrintColumn;
        }
        /*TUICard = PrintCardClass.createCard(TUIMap[firstPrintableCard[0]][firstPrintableCard[1]], true);
        counter = firstPrintableCard[0]-(leftMostPrintableCard[0]-(firstPrintableCard[1]-leftMostPrintableCard[1]));
        System.out.println(counter);
        for(int i=0; i<TUICard.length; i++){
            System.out.print(space.repeat(counter*2)+TUICard[i]+"\n");
        }
        TUICard=PrintCardClass.createCard(TUIMap[leftMostPrintableCard[0]][leftMostPrintableCard[1]], true);
        for(int i=0; i<TUICard.length; i++){
            System.out.print(TUICard[i]+"\n");
        }*/
    }

    public static int[] leftMostPrintableCardPos(Card[][] mapArray){
        int i = 0, j = 0, maxRow = 0, maxCol = 0, minRow = 0, minCol = 0, counter = 0;
        boolean isGrowing = true;
        Card card = null;
        while(card == null && maxRow <= mapArray.length && maxCol <= mapArray[0].length && minRow < mapArray[0].length && minCol < mapArray[0].length){
            card = mapArray[i][j];
            counter++;
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
        return new int[]{i,j};
    }

    public static int[] firstPrintableCardPos(Card[][] mapArray){
        int i = 0, j = mapArray.length-1, maxRow = 0 , minRow = 0, minCol = mapArray.length-1, maxCol = mapArray[0].length-1, counter = 0;
        boolean isGrowing = true;
        Card card = null;
        while(card == null && maxRow >= 0 && minCol >= 0 && minRow < mapArray.length && minCol < mapArray[0].length){
            card = mapArray[i][j];
            counter++;
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
        return new int[]{i,j};
    }

    public static int[] lastPrintableCardPos(Card[][] mapArray){
        int i=mapArray.length-1, j=0, maxRow = mapArray.length-1, maxCol = 0, minRow = mapArray.length-1, minCol = 0, counter = 0;
        boolean isGrowing = true;
        Card card = null;
        while (card == null && i >=0 && j<mapArray.length){
            card = mapArray[i][j];
            if(card==null){
                if (i == minRow) {
                    if(minRow == 0 || isGrowing == false) {
                        isGrowing = false;
                        maxRow--;
                        i=maxRow;
                    }else{
                        minRow--;
                        i=maxRow;
                    }
                }else{
                    i--;
                }
                if (j == minCol) {
                    if(maxCol == mapArray.length-1 || isGrowing == false) {
                        isGrowing = false;
                        minCol++;
                        j=maxCol;
                    }else{
                        maxCol++;
                        j=maxCol;
                    }
                }else{
                    j--;
                }
            }
        }
        return new int[]{i,j};
    }

    public static int[] lastCardInLinePos(Card[][] mapArray, int row, int col){
        int[] result = new int[2];
        while (row < mapArray.length && col < mapArray[0].length){
            if(mapArray[row][col]!=null){
                result = new int[]{row,col};
            }
            row++;
            col++;
        }
        return result;
    }
    public static int[] firstCardInLinePos(Card[][] mapArray, int col, int row) {
        int temp;
            if (col > row) {
                temp = row;
                row = 0;
                col = col - temp;
            } else {
                temp = col;
                col = 0;
                row = row - temp;
            }
            while(mapArray[row][col]==null) {
                if (mapArray[row][col] == null) {
                    row++;
                    col++;
                }
            }
        return new int[]{row, col};
    }

    public static Card[][] mapRearranger(Card[][] mapArray){
        return null;
    }
}
