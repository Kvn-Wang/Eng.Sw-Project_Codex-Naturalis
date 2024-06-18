package it.polimi.codexnaturalis.view.TUI;

import it.polimi.codexnaturalis.model.enumeration.ResourceType;
import it.polimi.codexnaturalis.model.player.GamePlayerMap;
import it.polimi.codexnaturalis.model.player.PlayerScoreResource;
import it.polimi.codexnaturalis.model.shop.card.Card;
import it.polimi.codexnaturalis.model.shop.card.StarterCard;
import it.polimi.codexnaturalis.utils.UtilCostantValue;

public class PrintMapClass {
    public static void main(String[] args) {
        String s ="╔══════╦═══════╦══════╗";
        int f = s.length();
    }

    public static void printMap(GamePlayerMap map){

    }

    public static int[] firstPrintableCardPos(Card[][] mapArray){
        int i=0, j=0;
        Card card = null;
        while(card == null){
            card = mapArray[i][j];
            i++;
            if(i >= UtilCostantValue.lunghezzaMaxMappa){
                i=0;
                j++;
            }
            if(j >= UtilCostantValue.lunghezzaMaxMappa){
                j=0;
            }
        }
        return new int[]{i-1,j};
    }
}
