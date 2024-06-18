package it.polimi.codexnaturalis.model.shop;

import it.polimi.codexnaturalis.model.enumeration.ShopType;
import it.polimi.codexnaturalis.model.shop.card.Card;
import it.polimi.codexnaturalis.network.util.networkMessage.MessageType;
import it.polimi.codexnaturalis.network.util.networkMessage.NetworkMessage;
import it.polimi.codexnaturalis.utils.PersonalizedException;
import it.polimi.codexnaturalis.utils.observer.Observer;

import java.util.ArrayList;

public class GeneralShop extends Shop {
    private Card visibleCard1;
    private Card visibleCard2;

    public GeneralShop(ShopType type, Observer observer) {
        super(type, observer);
        visibleCard1 = drawTopDeckCard();
        visibleCard2 = drawTopDeckCard();
    }

    public Card drawFromShopPlayer(int numCard){
        System.out.println("drawing card: "+numCard);
        Card supp;

        switch(numCard) {
            case 0:
                //ritorna il vecchio topDeckCard e "pesca" una nuova
                supp = topDeckCard;
                topDeckCard = drawTopDeckCard();
                try {
                    notifyObserverSingle(new NetworkMessage(MessageType.DRAW_CARD_UPDATE_SHOP_CARD_POOL,
                            argsGenerator(topDeckCard), String.valueOf(shopType), String.valueOf(0)));
                } catch (PersonalizedException.InvalidRequestTypeOfNetworkMessage e) {
                    throw new RuntimeException(e);
                }
                return supp;

            case 1:
                supp = visibleCard1;
                visibleCard1 = drawTopDeckCard();
                try {
                    notifyObserverSingle(new NetworkMessage(MessageType.DRAW_CARD_UPDATE_SHOP_CARD_POOL,
                            argsGenerator(visibleCard1), String.valueOf(shopType), String.valueOf(0)));
                } catch (PersonalizedException.InvalidRequestTypeOfNetworkMessage e) {
                    throw new RuntimeException(e);
                }
                return supp;
            case 2:
                supp = visibleCard2;
                visibleCard2 = drawTopDeckCard();
                try {
                    notifyObserverSingle(new NetworkMessage(MessageType.DRAW_CARD_UPDATE_SHOP_CARD_POOL,
                            argsGenerator(visibleCard2), String.valueOf(shopType), String.valueOf(0)));
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

    public Card getVisibleCard1() {
        return visibleCard1;
    }

    public Card getVisibleCard2() {
        return visibleCard2;
    }
}
