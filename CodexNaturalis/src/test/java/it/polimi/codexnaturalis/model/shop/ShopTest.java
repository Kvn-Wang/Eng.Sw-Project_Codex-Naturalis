package it.polimi.codexnaturalis.model.shop;

import it.polimi.codexnaturalis.model.enumeration.ShopType;
import it.polimi.codexnaturalis.model.shop.card.Card;
import it.polimi.codexnaturalis.model.shop.card.StarterCard;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShopTest {
    Shop testShop;

    @Test
    public void gen(){

    }
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
        testShop = new Shop(ShopType.STARTER);
        Card testStarterCard = testShop.drawTopDeckCard();
        System.out.println(testStarterCard.getPng());
        testShop = new Shop(ShopType.OBJECTIVE);
        testStarterCard = testShop.drawTopDeckCard();
        System.out.println(testStarterCard.getPng());
        testShop = new Shop(ShopType.RESOURCE);
        testStarterCard = testShop.drawTopDeckCard();
        System.out.println(testStarterCard.getPng());
    }
}