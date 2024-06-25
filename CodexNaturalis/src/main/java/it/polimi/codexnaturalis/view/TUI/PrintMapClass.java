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
    private static final String ANSI_RESET = "\u001B[0m";
    private static String ANSI_COLOR = PrintSymbols.convertColor(ResourceType.NONE);
    private static ResourceCard fillerCard = new ResourceCard(-1, ResourceType.NONE, ResourceType.NONE, ResourceType.NONE, ResourceType.NONE, ResourceType.NONE, 0);
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
        printYourMap(map);

    }
    public static void printYourMap(GamePlayerMap map) {
        Card[][] TUIMap;
        try {
            TUIMap = mapRearranger(map);
        } catch (PersonalizedException.InvalidPlaceCardRequirementException e) {
            throw new RuntimeException(e);
        } catch (PersonalizedException.InvalidPlacementException e) {
            throw new RuntimeException(e);
        }
        printMap(TUIMap);
    }
    public static void printMap(Card[][] TUIMap){
        int[] leftMostPrintableCard;
        int[] firstPrintableCard;
        int[] lastPrintableCard;
        int counter = 1, line=0;
        String[] TUICard = new String[500];
        String space = " ".repeat(2);
        leftMostPrintableCard= leftMostPrintableCardPos(TUIMap);
        firstPrintableCard= firstPrintableCardPos(TUIMap);
        lastPrintableCard = lastPrintableCardPos(TUIMap);
        int lastRow = lastPrintableCard[0]+1;
        int lastCol = lastPrintableCard[1]+1;
        int firstPrintRow = firstPrintableCard[0];
        int firstPrintColumn = firstPrintableCard[1];
        int printRow = firstPrintableCard[0];
        int printColumn = firstPrintableCard[1];
        int[] lastCardInLine = lastCardInLinePos(TUIMap, firstPrintRow, firstPrintColumn);
        String[] card = new String[5];
        boolean isFinished = false;
        while(!isFinished){
            lastCardInLine = lastCardInLinePos(TUIMap, firstPrintRow, firstPrintColumn);
            int distancefromleft=(firstPrintColumn+firstPrintRow)-(leftMostPrintableCard[1]+leftMostPrintableCard[0]);
            for(int i=0; i<card.length;i++){
                TUICard[line+i]=space.repeat(distancefromleft);
            }
            do{
                if(TUIMap[printRow][printColumn] == null){
                    for(int i=0; i<card.length; i++ ){
                        TUICard[line+i] =TUICard[line+i]+ space.repeat(3);
                    }
                }else if(TUIMap[printRow][printColumn].getPng() == -1){
                    TUICard[line] = TUICard[line] + ANSI_COLOR + "╔═══╗" + ANSI_RESET;
                    TUICard[line+1] = TUICard[line+1] + ANSI_COLOR + "║   ║" + ANSI_RESET;
                    if(counter<10){
                        TUICard[line+2] = TUICard[line+2] + ANSI_COLOR + "║" + " " + counter + " " + ANSI_RESET + ANSI_COLOR+"║" + ANSI_RESET;
                    }else if(10 < counter && counter<100){
                        TUICard[line+2] = TUICard[line+2] + ANSI_COLOR + "║"+ " " + counter + ANSI_RESET + ANSI_COLOR+"║" + ANSI_RESET;
                    }else{
                        TUICard[line+2] = TUICard[line+2] + ANSI_COLOR + "║"+ counter + ANSI_RESET + ANSI_COLOR+"║" + ANSI_RESET;
                    }
                    TUICard[line+3] = TUICard[line+3] + ANSI_COLOR + "║   ║" + ANSI_RESET;
                    TUICard[line+4] = TUICard[line+4] + ANSI_COLOR + "╚═══╝" + ANSI_RESET;
                    counter++;
                }else{
                    card = PrintCardClass.createCard(TUIMap[printRow][printColumn], !TUIMap[printRow][printColumn].getIsBack());
                    for(int i=0; i<5; i++ ){
                        TUICard[line+i] =(TUICard[line+i] == null ? "": TUICard[line+i])+ card[i];
                    }
                }
                printRow++;
                printColumn++;

            }while(printRow != lastCardInLine[0]+1 && printColumn != lastCardInLine[1]+1);
            if(printRow == lastRow && printColumn == lastCol){
                isFinished = true;
            }else{
                if(firstPrintColumn == 0){
                    firstPrintRow++;
                }
                else{
                    firstPrintColumn--;
                }
                int temp = firstPrintRow;
                firstPrintRow = firstCardInLinePos(TUIMap, firstPrintColumn,temp)[0];
                firstPrintColumn = firstCardInLinePos(TUIMap, firstPrintColumn,temp)[1];
                printRow = firstPrintRow;
                printColumn = firstPrintColumn;
                line = line+5;
            }
        }
        for(int i=0; i<TUICard.length; i++){
            if(TUICard[i] != null) {
                System.out.print(TUICard[i] + "\n");
            }
        }
        /*while(firstPrintRow != lastRow || firstPrintColumn != lastCol){
            lastCardInLine = lastCardInLinePos(TUIMap, firstPrintColumn, firstPrintColumn);
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
                if(firstPrintRow < firstPrintColumn){
                    int[] newFirstCard = firstCardInLinePos(TUIMap, firstPrintColumn-1, firstPrintRow);
                    firstPrintRow =newFirstCard[0];
                    firstPrintColumn  =newFirstCard[1];
                }else{
                    int[] newFirstCard = firstCardInLinePos(TUIMap, firstPrintColumn, firstPrintRow+1);
                    firstPrintRow =newFirstCard[0];
                    firstPrintColumn  =newFirstCard[1];
                }
                printRow = firstPrintRow;
                printColumn = firstPrintColumn;
            }

            System.out.println(line);
            if(firstPrintRow < firstPrintColumn){
                int[] newFirstCard = firstCardInLinePos(TUIMap, firstPrintColumn-1, firstPrintRow);
                firstPrintRow =newFirstCard[0];
                firstPrintColumn  =newFirstCard[1];
            }else{
                int[] newFirstCard = firstCardInLinePos(TUIMap, firstPrintColumn, firstPrintRow+1);
                firstPrintRow =newFirstCard[0];
                firstPrintColumn  =newFirstCard[1];
            }
            printRow = firstPrintRow;
            printColumn = firstPrintColumn;
        }
        for(int i=0; i<TUICard.length; i++ ){
            System.out.println(TUICard[i]);
        }
        TUICard = PrintCardClass.createCard(TUIMap[firstPrintableCard[0]][firstPrintableCard[1]], true);
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

    public static Card[][] mapRearranger(GamePlayerMap gamePlayerMap) throws PersonalizedException.InvalidPlaceCardRequirementException, PersonalizedException.InvalidPlacementException {
        Card [][] tempGamePlayerMap = gamePlayerMap.getMapArray();
        Card[][] mapArray = new Card[UtilCostantValue.lunghezzaMaxMappa][UtilCostantValue.lunghezzaMaxMappa];
        for(int i = 0 ; i < UtilCostantValue.lunghezzaMaxMappa ; i++){
            for(int j = 0 ; j < UtilCostantValue.lunghezzaMaxMappa ; j++){
                if(tempGamePlayerMap[i][j] !=null && tempGamePlayerMap[i][j].getPng() != -1){
                    mapArray[i][j] = tempGamePlayerMap[i][j];
                }else if (gamePlayerMap.getCheckValidPosition(i,j) != -1){
                    mapArray[i][j] = fillerCard;
                }
            }
        }
        return mapArray;
    }
}
