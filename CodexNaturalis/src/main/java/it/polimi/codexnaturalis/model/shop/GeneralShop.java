package it.polimi.codexnaturalis.model.shop;

import it.polimi.codexnaturalis.model.enumeration.ShopType;
import it.polimi.codexnaturalis.model.shop.card.Card;
import it.polimi.codexnaturalis.network.util.MessageType;
import it.polimi.codexnaturalis.network.util.NetworkMessage;
import it.polimi.codexnaturalis.utils.PersonalizedException;
import it.polimi.codexnaturalis.utils.observer.Observer;

import java.io.Serializable;

public class GeneralShop extends Shop {
    private Card visibleCard1;
    private Card visibleCard2;

    public GeneralShop(ShopType type, Observer observer) {
        super(type, observer);
        visibleCard1 = drawTopDeckCard();
        visibleCard2 = drawTopDeckCard();
    }

    public Card drawFromShopPlayer(int numCard){
        System.out.printf("\ndrawing card\n");
        Card supp;

        switch(numCard) {
            case 1:
                supp = visibleCard1;
                visibleCard1 = drawTopDeckCard();
                try {
                    notifyObserver(new NetworkMessage(MessageType.SHOP_UPDATE, argsGenerator(visibleCard1), "visibleCard1", argsGenerator(shopType)));
                } catch (PersonalizedException.InvalidRequestTypeOfNetworkMessage e) {
                    throw new RuntimeException(e);
                }
                return supp;
            case 2:
                supp = visibleCard2;
                visibleCard2 = drawTopDeckCard();
                try {
                    notifyObserver(new NetworkMessage(MessageType.SHOP_UPDATE, argsGenerator(visibleCard2), "visibleCard2", argsGenerator(shopType)));
                } catch (PersonalizedException.InvalidRequestTypeOfNetworkMessage e) {
                    throw new RuntimeException(e);
                }
                return supp;
            default:
                throw new RuntimeException("è stata richiesta un numero di carta non valido: "+numCard);
        }
    }
    public Boolean checkEmptyShop(){
        if(visibleCard1==null && visibleCard2==null && topDeckCard==null){
            return true;
        }
        else
            return false;
    }
}
