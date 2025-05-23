package it.polimi.codexnaturalis.view.TUI;

import it.polimi.codexnaturalis.model.enumeration.CardCorner;
import it.polimi.codexnaturalis.model.enumeration.ResourceType;
import it.polimi.codexnaturalis.model.player.GamePlayerMap;
import it.polimi.codexnaturalis.model.player.PlayerScoreResource;
import it.polimi.codexnaturalis.model.shop.card.Card;
import it.polimi.codexnaturalis.model.shop.card.ResourceCard;
import it.polimi.codexnaturalis.model.shop.card.StarterCard;
import it.polimi.codexnaturalis.utils.PersonalizedException;
import it.polimi.codexnaturalis.utils.UtilCostantValue;

import java.util.Arrays;
import java.util.HashMap;

// numero di spazio da spostare formula Ncarte -1 * 23 +15
public class PrintMapClass {
    private static final String ANSI_RESET = "\u001B[0m";
    private static String ANSI_COLOR = PrintSymbols.convertColor(ResourceType.NONE);
    private static ResourceCard fillerCard = new ResourceCard(-1, ResourceType.NONE, ResourceType.NONE, ResourceType.NONE, ResourceType.NONE, ResourceType.NONE, 0);
    public PrintMapClass() {
    }

    private static int publicCounter=0;
    private static HashMap<Integer, Integer[]> freePos = new HashMap<Integer, Integer[]>();

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
        printYourMap(map.getMapArray());
        try {
            map.placeCard((UtilCostantValue.lunghezzaMaxMappa/2), UtilCostantValue.lunghezzaMaxMappa/2-2, firstTestCard);
        } catch (PersonalizedException.InvalidPlacementException e) {
            throw new RuntimeException(e);
        } catch (PersonalizedException.InvalidPlaceCardRequirementException e) {
            throw new RuntimeException(e);
        }
        printYourMap(map.getMapArray());
    }
    public static void printYourMap(Card[][] map) {
        Card[][] TUIMap;
        try {
            TUIMap = mapRearranger(map);
        } catch (PersonalizedException.InvalidPlaceCardRequirementException e) {
            throw new RuntimeException(e);
        } catch (PersonalizedException.InvalidPlacementException e) {
            throw new RuntimeException(e);
        }
        newPrintMap(TUIMap);
    }
    public static void printMap(Card[][] TUIMap){
        int[] leftMostPrintableCard;
        int[] firstPrintableCard;
        int[] lastPrintableCard;
        int counter = 1, line=0;
        String[] TUICard = new String[500];
        String space = " ".repeat(3);
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
                    TUICard[line] = TUICard[line] + ANSI_COLOR + "╔════╗" + ANSI_RESET;
                    TUICard[line+1] = TUICard[line+1] + ANSI_COLOR + "║    ║" + ANSI_RESET;
                    if(counter<10){
                        TUICard[line+2] = TUICard[line+2] + ANSI_COLOR + "║" + " " + counter + "  " + ANSI_RESET + ANSI_COLOR+"║" + ANSI_RESET;
                    }else if(10 < counter && counter<100){
                        TUICard[line+2] = TUICard[line+2] + ANSI_COLOR + "║"+ " " + counter +" " + ANSI_RESET + ANSI_COLOR+"║" + ANSI_RESET;
                    }else{
                        TUICard[line+2] = TUICard[line+2] + ANSI_COLOR + "║"+ counter + " " + ANSI_RESET + ANSI_COLOR+"║" + ANSI_RESET;
                    }
                    TUICard[line+3] = TUICard[line+3] + ANSI_COLOR + "║    ║" + ANSI_RESET;
                    TUICard[line+4] = TUICard[line+4] + ANSI_COLOR + "╚════╝" + ANSI_RESET;
                    Integer[] coordinates = new Integer[2];
                    coordinates[0] = printRow;
                    coordinates[1] = printColumn;
                    freePos.put(counter,coordinates);
                    counter++;
                }else{
                    //card = PrintCardClass.createCard(TUIMap[printRow][printColumn], !TUIMap[printRow][printColumn].getIsBack());
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
                line = line+3;
            }
        }
        for(int i=0; i<TUICard.length; i++){
            if(TUICard[i] != null) {
                System.out.print(TUICard[i] + "\n");
            }
        }
        publicCounter = counter;
    }

    public static void newPrintMap(Card[][] TUIMap){
        int[] leftMostPrintableCard;
        int[] firstPrintableCard;
        int[] lastPrintableCard;
        int counter = 1, line=0, column=0;
        String[][] TUICard = new String[UtilCostantValue.lunghezzaMaxMappa*5][UtilCostantValue.lunghezzaMaxMappa*6];
        String space = " ";
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
        String[][] card = new String[5][6];
        boolean isFinished = false;
        freePos.clear();
        while(!isFinished){
            lastCardInLine = lastCardInLinePos(TUIMap, firstPrintRow, firstPrintColumn);
            int distancefromleft=(firstPrintColumn+firstPrintRow)-(leftMostPrintableCard[1]+leftMostPrintableCard[0]);
            for(int i=0; i<card.length; i++){
                for(int j=0; j<distancefromleft*3; j++){
                    if(TUICard[line+i][column+j] == " " || TUICard[line+i][column+j] == null ) {
                        TUICard[line + i][column + j] = space;
                    }
                }
            }
            column = column + distancefromleft*3;
            do{
                if(TUIMap[printRow][printColumn] == null){
                    for(int i=0; i<5; i++ ){
                        for(int j=0; j<6; j++) {
                            if(TUICard[line+i][column+j] == null) {
                                TUICard[line + i][column + j] = space;
                            }
                        }
                    }
                }else if(TUIMap[printRow][printColumn].getPng() == -1){
                    if(TUIMap[printRow-1][printColumn] == null || TUIMap[printRow-1][printColumn].getPng() == -1){
                        TUICard[line][column] =ANSI_COLOR + "╔";TUICard[line][column+1]="═";TUICard[line][column+2]="═";
                        TUICard[line+1][column] =ANSI_COLOR + "║";TUICard[line+1][column+1]=" ";TUICard[line+1][column+2]=" ";
                    }
                    if(TUIMap[printRow][printColumn+1] == null || TUIMap[printRow][printColumn+1].getPng() == -1){
                        TUICard[line][column+3]="═";TUICard[line][column+4]="═";TUICard[line][column+5]="╗" + ANSI_RESET;
                        TUICard[line+1][column+3]=" ";TUICard[line+1][column+4]=" ";TUICard[line+1][column+5]="║" + ANSI_RESET;
                    }
                    //TUICard[line][column] =ANSI_COLOR + "╔";TUICard[line][column+1]="═";TUICard[line][column+2]="═";TUICard[line][column+3]="═";TUICard[line][column+4]="═";TUICard[line][column+5]="╗" + ANSI_RESET;
                    //TUICard[line+1][column] =ANSI_COLOR + "║";TUICard[line+1][column+1]=" ";TUICard[line+1][column+2]=" ";TUICard[line+1][column+3]=" ";TUICard[line+1][column+4]=" ";TUICard[line+1][column+5]="║" + ANSI_RESET;
                    if(counter<10){
                        TUICard[line+2][column] =ANSI_COLOR + "║";TUICard[line+2][column+1]= " ";TUICard[line+2][column+2]= String.valueOf(counter);TUICard[line+2][column+3]=" ";TUICard[line+2][column+4]=" " + ANSI_RESET;TUICard[line+2][column+5]= ANSI_COLOR+"║" + ANSI_RESET;
                    }else if(10 < counter && counter<100){
                        TUICard[line+2][column] = ANSI_COLOR + "║";TUICard[line+2][column+1]= " ";TUICard[line+2][column+2]= String.valueOf(counter/10); TUICard[line+2][column+3]=String.valueOf(counter%10);TUICard[line+2][column+4] =" ";TUICard[line+2][column+5]= ANSI_RESET + ANSI_COLOR+"║" + ANSI_RESET;
                    }else{
                        TUICard[line+2][column] = ANSI_COLOR + "║";TUICard[line+2][column+1]= String.valueOf(counter/100);TUICard[line+2][column+2]= String.valueOf((counter%100)/10);TUICard[line+2][column+3]=String.valueOf((counter%100)%10);TUICard[line+2][column+4]= " ";TUICard[line+2][column+5]= ANSI_RESET + ANSI_COLOR+"║" + ANSI_RESET;
                    }
                    if(TUIMap[printRow][printColumn-1]== null || TUIMap[printRow][printColumn-1].getPng() == -1){
                        TUICard[line+3][column] = ANSI_COLOR + "║";TUICard[line+3][column+1]=" ";TUICard[line+3][column+2]=" ";
                        TUICard[line+4][column] = ANSI_COLOR + "╚";TUICard[line+4][column+1]="═";TUICard[line+4][column+2]="═";
                    }
                    if(TUIMap[printRow+1][printColumn] == null || TUIMap[printRow+1][printColumn].getPng() == -1){
                        TUICard[line+3][column+3]=" ";TUICard[line+3][column+4]=" ";TUICard[line+3][column+5]="║" + ANSI_RESET;
                        TUICard[line+4][column+3]="═";TUICard[line+4][column+4]="═";TUICard[line+4][column+5]="╝" + ANSI_RESET;
                    }
                    //TUICard[line+3][column] = ANSI_COLOR + "║";TUICard[line+3][column+1]=" ";TUICard[line+3][column+2]=" ";TUICard[line+3][column+3]=" ";TUICard[line+3][column+4]=" ";TUICard[line+3][column+5]="║" + ANSI_RESET;
                    //TUICard[line+4][column] = ANSI_COLOR + "╚";TUICard[line+4][column+1]="═";TUICard[line+4][column+2]="═";TUICard[line+4][column+3]="═";TUICard[line+4][column+4]="═";TUICard[line+4][column+5]="╝" + ANSI_RESET;
                    Integer[] coordinates = new Integer[2];
                    coordinates[0] = printRow;
                    coordinates[1] = printColumn;
                    freePos.put(counter,coordinates);
                    counter++;
                }else{
                    card=PrintCardClass.createCard(TUIMap[printRow][printColumn], !TUIMap[printRow][printColumn].getIsBack());
                    if(TUIMap[printRow-1][printColumn] == null || TUIMap[printRow-1][printColumn] == fillerCard || (TUIMap[printRow][printColumn].getPlacedOrder()>TUIMap[printRow-1][printColumn].getPlacedOrder())){
                        TUICard[line][column]= card[0][0];
                        TUICard[line][column+1]= card[0][1];
                        TUICard[line][column+2]= card[0][2];
                        TUICard[line+1][column]= card[1][0];
                        TUICard[line+1][column+1]= card[1][1];
                        TUICard[line+1][column+2]= card[1][2];
                    }
                    if(TUIMap[printRow][printColumn+1] == null || TUIMap[printRow][printColumn+1] == fillerCard || (TUIMap[printRow][printColumn].getPlacedOrder()>TUIMap[printRow][printColumn+1].getPlacedOrder())){
                        TUICard[line][column+3]= card[0][3];
                        TUICard[line][column+4]= card[0][4];
                        TUICard[line][column+5]= card[0][5];
                        TUICard[line+1][column+3]= card[1][3];
                        TUICard[line+1][column+4]= card[1][4];
                        TUICard[line+1][column+5]= card[1][5];
                    }
                    TUICard[line+2][column]=card[2][0];
                    TUICard[line+2][column+1]=card[2][1];
                    TUICard[line+2][column+2]=card[2][2];
                    TUICard[line+2][column+3]=card[2][3];
                    TUICard[line+2][column+4]=card[2][4];
                    TUICard[line+2][column+5]=card[2][5];
                    if(TUIMap[printRow][printColumn-1] == null || TUIMap[printRow][printColumn-1] == fillerCard || TUIMap[printRow][printColumn].getPlacedOrder()>TUIMap[printRow][printColumn-1].getPlacedOrder()){
                        TUICard[line+3][column]= card[3][0];
                        TUICard[line+3][column+1]= card[3][1];
                        TUICard[line+3][column+2]= card[3][2];
                        TUICard[line+4][column]= card[4][0];
                        TUICard[line+4][column+1]= card[4][1];
                        TUICard[line+4][column+2]= card[4][2];
                    }
                    if(TUIMap[printRow+1][printColumn] == null || TUIMap[printRow+1][printColumn] == fillerCard || ((TUIMap[printRow+1][printColumn] != null && TUIMap[printRow+1][printColumn] != fillerCard)&&(TUIMap[printRow][printColumn].getPlacedOrder()>TUIMap[printRow+1][printColumn].getPlacedOrder()))){
                        TUICard[line+3][column+3]= card[3][3];
                        TUICard[line+3][column+4]= card[3][4];
                        TUICard[line+3][column+5]= card[3][5];
                        TUICard[line+4][column+3]= card[4][3];
                        TUICard[line+4][column+4]= card[4][4];
                        TUICard[line+4][column+5]= card[4][5];
                    }
                }
                column=column+6;
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
                line = line+3;
                column = 0;
            }
        }
        isFinished=false;
        int i=0, j=0;
        String printable="";
        while(TUICard[i][0]!=null){
            while (TUICard[i][j]!=null){
                printable = printable+TUICard[i][j];
                j++;
            }
            System.out.println(printable);
            printable = "";
            i++;
            j=0;
        }
        publicCounter = counter;
    }

    public static HashMap<Integer, Integer[]> getFreePos() {
        return freePos;
    }

    public static int getPublicCounter() {
        return publicCounter;
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
    private static int getCheckValidPosition(int x, int y, Card[][] mapArray){
        int adiacentNumCard = -1;
        if(checkValidityXY(x,y, mapArray)){
            try {
                adiacentNumCard = checkValidPosition(x,y, mapArray);
            } catch (PersonalizedException.InvalidPlacementException e) {
                return adiacentNumCard;
            }
        }
        return adiacentNumCard;
    }

    private static int checkValidPosition(int x, int y, Card[][] mapArray) throws PersonalizedException.InvalidPlacementException {
        int adiacentNumCard = 0;

        //controllo di adiacenza della carta facendo attenzione ai valori limite
        if(x == UtilCostantValue.lunghezzaMaxMappa - 1) {
            if(!(mapArray[x - 1][y] == null)) {
                //controllo corner
                if(mapArray[x - 1][y].getCardCorner(CardCorner.SOUTH) == null || mapArray[x - 1][y].getCardCorner(CardCorner.SOUTH) == ResourceType.UNASSIGNABLE) {
                    throw new PersonalizedException.InvalidPlacementException();
                }
                adiacentNumCard++;
            }
        } else if(x == 0) {
            if(!(mapArray[x + 1][y] == null)) {
                //controllo del corner
                if(mapArray[x + 1][y].getCardCorner(CardCorner.NORTH) == null || mapArray[x + 1][y].getCardCorner(CardCorner.NORTH) == ResourceType.UNASSIGNABLE) {
                    throw new PersonalizedException.InvalidPlacementException();
                }
                adiacentNumCard++;
            }
        } else {
            if(!(mapArray[x + 1][y] == null)) {
                if(mapArray[x + 1][y].getCardCorner(CardCorner.NORTH) == null || mapArray[x + 1][y].getCardCorner(CardCorner.NORTH) == ResourceType.UNASSIGNABLE) {
                    throw new PersonalizedException.InvalidPlacementException();
                }
                adiacentNumCard++;
            }
            if(!(mapArray[x - 1][y] == null)) {
                if(mapArray[x - 1][y].getCardCorner(CardCorner.SOUTH) == null || mapArray[x - 1][y].getCardCorner(CardCorner.SOUTH) == ResourceType.UNASSIGNABLE) {
                    throw new PersonalizedException.InvalidPlacementException();
                }
                adiacentNumCard++;
            }
        }

        if(y == UtilCostantValue.lunghezzaMaxMappa - 1) {
            if(!(mapArray[x][y - 1] == null)) {
                //controllo del corner
                if(mapArray[x][y - 1].getCardCorner(CardCorner.EAST) == null || mapArray[x][y - 1].getCardCorner(CardCorner.EAST) == ResourceType.UNASSIGNABLE) {
                    throw new PersonalizedException.InvalidPlacementException();
                }
                adiacentNumCard++;
            }
        } else if(y == 0) {
            if(!(mapArray[x][y + 1] == null)) {
                //controllo del corner
                if(mapArray[x][y + 1].getCardCorner(CardCorner.WEST) == null || mapArray[x][y + 1].getCardCorner(CardCorner.WEST) == ResourceType.UNASSIGNABLE) {
                    throw new PersonalizedException.InvalidPlacementException();
                }
                adiacentNumCard++;
            }
        } else {
            if(!(mapArray[x][y + 1] == null)) {
                //controllo del corner
                if(mapArray[x][y + 1].getCardCorner(CardCorner.WEST) == null || mapArray[x][y + 1].getCardCorner(CardCorner.WEST) == ResourceType.UNASSIGNABLE) {
                    throw new PersonalizedException.InvalidPlacementException();
                }
                adiacentNumCard++;
            }
            if(!(mapArray[x][y - 1] == null)) {
                //controllo del corner
                if(mapArray[x][y - 1].getCardCorner(CardCorner.EAST) == null || mapArray[x][y - 1].getCardCorner(CardCorner.EAST) == ResourceType.UNASSIGNABLE) {
                    throw new PersonalizedException.InvalidPlacementException();
                }
                adiacentNumCard++;
            }
        }

        if(adiacentNumCard == 0 && mapArray[UtilCostantValue.lunghezzaMaxMappa/2][UtilCostantValue.lunghezzaMaxMappa/2]!=null) {
            throw new PersonalizedException.InvalidPlacementException();
        }
        return adiacentNumCard;
    }
    private static boolean checkValidityXY(int x, int y, Card[][] mapArray) {
        if(x < 0 || y < 0 || x >= UtilCostantValue.lunghezzaMaxMappa || y >= UtilCostantValue.lunghezzaMaxMappa) {
            return false;
        } else {
            if (mapArray[x][y] == null) {
                return true;
            } else {
                return false;
            }
        }
    }

    public static Card[][] mapRearranger(Card[][] tempGamePlayerMap) throws PersonalizedException.InvalidPlaceCardRequirementException, PersonalizedException.InvalidPlacementException {

        Card[][] mapArray = new Card[UtilCostantValue.lunghezzaMaxMappa][UtilCostantValue.lunghezzaMaxMappa];
        for(int i = 0 ; i < UtilCostantValue.lunghezzaMaxMappa ; i++){
            for(int j = 0 ; j < UtilCostantValue.lunghezzaMaxMappa ; j++){
                if(tempGamePlayerMap[i][j] !=null){
                    mapArray[i][j] = tempGamePlayerMap[i][j];
                }else if (getCheckValidPosition(i,j, tempGamePlayerMap) != -1){
                    mapArray[i][j] = fillerCard;
                }
            }
        }
        return mapArray;
    }
}
