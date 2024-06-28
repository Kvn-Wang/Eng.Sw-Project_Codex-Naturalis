package it.polimi.codexnaturalis.model.shop;

import it.polimi.codexnaturalis.model.enumeration.ShopType;
import it.polimi.codexnaturalis.model.shop.card.Card;
import it.polimi.codexnaturalis.network.util.networkMessage.NetworkMessage;
import it.polimi.codexnaturalis.utils.PersonalizedException;
import it.polimi.codexnaturalis.utils.observer.Observer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class GeneralShopTest {
    GeneralShop testGeneralShop;
    ShopType[] shopList = {ShopType.OBJECTIVE, ShopType.RESOURCE};
    Observer observer = new Observer() {
        @Override
        public void update(NetworkMessage message){

        }
    };

    @Test
    public void testGeneralShop() {
        testGeneralShop = new GeneralShop(ShopType.RESOURCE, observer);
        assertEquals(testGeneralShop.shopType, ShopType.RESOURCE);
        testGeneralShop = new GeneralShop(ShopType.STARTER, observer);
        assertEquals(testGeneralShop.shopType, ShopType.STARTER);
        testGeneralShop = new GeneralShop(ShopType.OBJECTIVE, observer);
        assertEquals(testGeneralShop.shopType, ShopType.OBJECTIVE);
    }

    @Test
    public void testDrawFromShopPlayer() {
        Card testCard;
        for (int i = 1; i < 3; i++) {
            for (ShopType testShop : shopList) {
                testGeneralShop = new GeneralShop(testShop, new Observer() {
                    @Override
                    public void update(NetworkMessage message) {

                    }
                });
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
            testGeneralShop = new GeneralShop(testShop, new Observer() {
                @Override
                public void update(NetworkMessage message) {

                }
            });
            assertEquals(false , testGeneralShop.checkEmptyShop());
            Card testCard = testGeneralShop.drawFromShopPlayer(1);
            while(!testGeneralShop.checkEmptyShop()){
                testCard = testGeneralShop.drawFromShopPlayer(1);
                testCard = testGeneralShop.drawFromShopPlayer(2);
                assertEquals(false , testGeneralShop.checkEmptyShop());
            }
            testCard = testGeneralShop.drawFromShopPlayer(2);
            assertEquals(true , testGeneralShop.checkEmptyShop());
        }
    }
}