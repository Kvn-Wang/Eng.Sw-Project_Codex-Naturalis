package it.polimi.codexnaturalis.model.shop;

import it.polimi.codexnaturalis.model.enumeration.ShopType;
import it.polimi.codexnaturalis.model.shop.card.Card;
import it.polimi.codexnaturalis.model.shop.card.StarterCard;
import it.polimi.codexnaturalis.network.util.networkMessage.NetworkMessage;
import it.polimi.codexnaturalis.utils.PersonalizedException;
import it.polimi.codexnaturalis.utils.observer.Observer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShopTest {
    Shop testShop;

    @Test
    public void gen(){

    }

    Observer observer = new Observer() {
        @Override
        public void update(NetworkMessage message) throws PersonalizedException.InvalidRequestTypeOfNetworkMessage {

        }
    };
    @Test
    public void testShop(){
        testShop = new Shop(ShopType.STARTER, observer);
        assertEquals(testShop.shopType, ShopType.STARTER);
        testShop = new Shop(ShopType.RESOURCE, observer);
        assertEquals(testShop.shopType, ShopType.RESOURCE);
        testShop = new Shop(ShopType.OBJECTIVE, observer);
        assertEquals(testShop.shopType, ShopType.OBJECTIVE);
    }

    @Test
    public void TestDrawFromDeckCard(){
        int checkpng;
        testShop = new Shop(ShopType.STARTER, observer);
        Card testStarterCard = testShop.drawTopDeckCard();
        System.out.println(testStarterCard.getPng());
        testShop = new Shop(ShopType.OBJECTIVE, observer);
        testStarterCard = testShop.drawTopDeckCard();
        System.out.println(testStarterCard.getPng());
        testShop = new Shop(ShopType.RESOURCE, observer);
        testStarterCard = testShop.drawTopDeckCard();
        System.out.println(testStarterCard.getPng());
    }
}