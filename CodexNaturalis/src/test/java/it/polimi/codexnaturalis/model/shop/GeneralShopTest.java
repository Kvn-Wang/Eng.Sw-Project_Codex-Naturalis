package it.polimi.codexnaturalis.model.shop;

import it.polimi.codexnaturalis.model.enumeration.ShopType;
import it.polimi.codexnaturalis.model.shop.card.Card;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class GeneralShopTest {
    GeneralShop testGeneralShop;
    ShopType[] shopList = {ShopType.STARTER, ShopType.OBJECTIVE, ShopType.RESOURCE};

    @Test
    public void testGeneralShop() {
        testGeneralShop = new GeneralShop(ShopType.RESOURCE);
        assertEquals(testGeneralShop.shopType, ShopType.RESOURCE);
        testGeneralShop = new GeneralShop(ShopType.STARTER);
        assertEquals(testGeneralShop.shopType, ShopType.STARTER);
        testGeneralShop = new GeneralShop(ShopType.OBJECTIVE);
        assertEquals(testGeneralShop.shopType, ShopType.OBJECTIVE);
    }

    @Test
    public void testDrawFromShopPlayer() {
        Card testCard;
        for (int i = 1; i < 3; i++) {
            for (ShopType testShop : shopList) {
                testGeneralShop = new GeneralShop(testShop);
                testCard = testGeneralShop.drawFromShopPlayer(i);
                System.out.println(": " +testCard.getPng());
            }
        }
        assertThrows(RuntimeException.class, ()-> testGeneralShop.drawFromShopPlayer(4));
        assertThrows(RuntimeException.class, ()-> testGeneralShop.drawFromShopPlayer(-1));

    }

    @Test
    public void testCheckEmptyShop(){
        for (ShopType testShop : shopList) {
            testGeneralShop = new GeneralShop(testShop);
            assertEquals(false , testGeneralShop.checkEmptyShop());
            Card testCard = testGeneralShop.drawFromShopPlayer(1);
            while(testCard!=null){
                testCard = testGeneralShop.drawFromShopPlayer(1);
                assertEquals(false , testGeneralShop.checkEmptyShop());
            }
            testCard = testGeneralShop.drawFromShopPlayer(2);
            assertEquals(true , testGeneralShop.checkEmptyShop());
        }
    }
}