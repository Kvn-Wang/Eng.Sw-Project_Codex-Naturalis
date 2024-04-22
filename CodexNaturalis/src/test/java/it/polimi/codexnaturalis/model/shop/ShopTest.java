package it.polimi.codexnaturalis.model.shop;

import it.polimi.codexnaturalis.model.enumeration.ShopType;
import it.polimi.codexnaturalis.model.shop.card.Card;
import it.polimi.codexnaturalis.model.shop.card.StarterCard;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShopTest {
    Shop testShop;
    @Test
    public void testShop(){
        testShop = new Shop(ShopType.STARTER);
        assertEquals(testShop.shopType, ShopType.STARTER);
        testShop = new Shop(ShopType.RESOURCE);
        assertEquals(testShop.shopType, ShopType.RESOURCE);
        testShop = new Shop(ShopType.OBJECTIVE);
        assertEquals(testShop.shopType, ShopType.OBJECTIVE);
    }

    @Test
    public void TestDrawFromDeckCard(){
        int checkpng;
        boolean result = false;
        testShop = new Shop(ShopType.STARTER);
        Card testStarterCard = testShop.drawTopDeckCard();
        //da cambiare;
        checkpng = testStarterCard.getPng();
        if(checkpng > 80 && checkpng < 87){
            result = true;
        }
        assertEquals(result,true);
        result = false;
        testShop = new Shop(ShopType.OBJECTIVE);
        Card testObjectiveCard = testShop.drawTopDeckCard();
        if(checkpng > 40 && checkpng < 81){
            result = true;
        }
        assertEquals(result,true);
        result = false;
        testShop = new Shop(ShopType.RESOURCE);
        Card testResourceCard = testShop.drawTopDeckCard();
        if(checkpng > 0 && checkpng < 41){
            result = true;
        }
        assertEquals(result,true);
    }
}