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
        int counter, line=0;
        String[] TUICard = new String[7];
        String space = " ".repeat(8);
        leftMostPrintableCard= leftMostPrintableCardPos(TUIMap);
        firstPrintableCard= firstPrintableCardPos(TUIMap);
        lastPrintableCard = lastPrintableCardPos(TUIMap);
        int lastRow = lastPrintableCard[0];
        int lastCol = lastPrintableCard[1];
        int firstPrintRow = firstPrintableCard[0];
        int firstPrintColumn = firstPrintableCard[1];
        int printRow = firstPrintableCard[0];
        int printColumn = firstPrintableCard[1];
        int[] lastCardInLine;
        String[] card = new String[7];
        boolean isFinished = false;
        while(!isFinished){
            lastCardInLine = lastCardInLinePos(TUIMap, firstPrintColumn, firstPrintColumn);
            do{
                int distancefromleft=(firstPrintColumn+firstPrintRow)-(leftMostPrintableCard[1]+leftMostPrintableCard[0]);
                for(int i=0; i<7;i++){
                    TUICard[line]=space.repeat(distancefromleft);
                }
                if(TUIMap[printRow][printColumn] == null){
                    for(int i=0; i<TUICard.length; i++ ){
                        TUICard[line] =TUICard[line]+ space.repeat(3);
                    }
                }else{
                    card = PrintCardClass.createCard(TUIMap[printRow][printColumn], true);
                    for(int i=0; i<7; i++ ){
                        TUICard[line] =(TUICard[line] == null ? "": TUICard[line])+ card[i];
                        line++;
                    }
                }
            }while(printRow != lastPrintableCard[0] && printColumn != lastPrintableCard[1]);
            if(printRow == lastRow && printColumn == lastCol){
                isFinished = true;
            }
        }
        /*while(firstPrintRow != lastRow || frstPrintColumn != lastCol){
            lastCardInLine = lastCardInLinePos(TUIMap, frstPrintColumn, frstPrintColumn);
            while (printRow!=lastCardInLine[0]-1 && printColumn!=lastCardInLine[1]-1){
                if(TUIMap[printRow][printColumn] == null){
                    for(int i=0; i<TUICard.length; i++ ){
                        TUICard[line] =TUICard[line]+ space.repeat(3);
                    }
                }else{
                    card = PrintCardClass.createCard(TUIMap[printRow][printColumn], true);
                    for(int i=0; i<7; i++ ){
                        TUICard[line] =(TUICard[line] == null ? "": TUICard[line])+ card[i];
                        line++;
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

            System.out.println(line);
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
        }*/
        for(int i=0; i<TUICard.length; i++ ){
            System.out.println(TUICard[i]);
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
        while(card == null && maxRow <= UtilCostantValue.lunghezzaMaxMappa && maxCol <= UtilCostantValue.lunghezzaMaxMappa && minRow < UtilCostantValue.lunghezzaMaxMappa && minCol < UtilCostantValue.lunghezzaMaxMappa){
            card = mapArray[i][j];
            int check = i;
            counter++;
            if(card == null) {
                if (i == maxRow) {
                    if (maxRow == UtilCostantValue.lunghezzaMaxMappa - 1) {
                        minRow++;
                    } else {
                        maxRow++;
                    }
                    i = minRow;
                } else {
                    i++;
                }
                if (j == minCol) {
                    if (maxCol == UtilCostantValue.lunghezzaMaxMappa - 1 || isGrowing == false) {
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
        int i = 0, j = UtilCostantValue.lunghezzaMaxMappa-1, maxRow = 0 , minRow = 0, minCol = UtilCostantValue.lunghezzaMaxMappa-1, maxCol = UtilCostantValue.lunghezzaMaxMappa-1, counter = 0;
        boolean isGrowing = true;
        Card card = null;
        while(card == null && maxRow >= 0 && minCol >= 0 && minRow < UtilCostantValue.lunghezzaMaxMappa && minCol < UtilCostantValue.lunghezzaMaxMappa){
            card = mapArray[i][j];
            counter++;
            if(card == null) {
                if (i == maxRow) {
                    if (maxRow == UtilCostantValue.lunghezzaMaxMappa - 1) {
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
        int i=UtilCostantValue.lunghezzaMaxMappa-1, j=0, maxRow = UtilCostantValue.lunghezzaMaxMappa-1, maxCol = 0, minRow = UtilCostantValue.lunghezzaMaxMappa-1, minCol = 0, counter = 0;
        boolean isGrowing = true;
        Card card = null;
        while (card == null && i >=0 && j<UtilCostantValue.lunghezzaMaxMappa){
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
                    if(maxCol == UtilCostantValue.lunghezzaMaxMappa-1 || isGrowing == false) {
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
        while (row < UtilCostantValue.lunghezzaMaxMappa && col < UtilCostantValue.lunghezzaMaxMappa){
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
