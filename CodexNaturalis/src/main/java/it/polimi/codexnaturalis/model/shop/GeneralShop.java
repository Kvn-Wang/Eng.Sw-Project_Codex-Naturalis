package it.polimi.codexnaturalis.model.shop;

import it.polimi.codexnaturalis.model.enumeration.ShopType;
import it.polimi.codexnaturalis.model.shop.card.Card;
import it.polimi.codexnaturalis.network.util.networkMessage.MessageType;
import it.polimi.codexnaturalis.network.util.networkMessage.NetworkMessage;
import it.polimi.codexnaturalis.utils.PersonalizedException;
import it.polimi.codexnaturalis.utils.observer.Observer;
import it.polimi.codexnaturalis.view.TUI.PrintCardClass;

import java.util.ArrayList;

/**
 * The type General shop.
 */
public class GeneralShop extends Shop {
    private Card visibleCard1;
    private Card visibleCard2;

    /**
     * Instantiates a new General shop.
     *
     * @param type     the type
     * @param observer the observer
     */
    public GeneralShop(ShopType type, Observer observer) {
        super(type, observer);
        visibleCard1 = drawTopDeckCard();
        visibleCard2 = drawTopDeckCard();
    }

    /**
     * Draw from shop player card.
     *
     * @param numCard the num card
     * @return the card
     */
    public Card drawFromShopPlayer(int numCard){
        System.out.println("drawing card: "+numCard);
        Card supp;

        switch(numCard) {
            case 0:
                //ritorna il vecchio topDeckCard e "pesca" una nuova
                supp = topDeckCard;
                topDeckCard = drawTopDeckCard();

                notifyObserverSingle(new NetworkMessage(MessageType.DRAW_CARD_UPDATE_SHOP_CARD_POOL,
                        argsGenerator(topDeckCard), String.valueOf(shopType), String.valueOf(0)));
                return supp;

            case 1:
                supp = visibleCard1;
                visibleCard1 = topDeckCard;
                topDeckCard = drawTopDeckCard();

                notifyObserverSingle(new NetworkMessage(MessageType.DRAW_CARD_UPDATE_SHOP_CARD_POOL,
                        argsGenerator(visibleCard1), String.valueOf(shopType), String.valueOf(1)));

                notifyObserverSingle(new NetworkMessage(MessageType.DRAW_CARD_UPDATE_SHOP_CARD_POOL,
                        argsGenerator(topDeckCard), String.valueOf(shopType), String.valueOf(0)));
                return supp;
            case 2:
                supp = visibleCard2;
                visibleCard2 = topDeckCard;
                topDeckCard = drawTopDeckCard();

                notifyObserverSingle(new NetworkMessage(MessageType.DRAW_CARD_UPDATE_SHOP_CARD_POOL,
                        argsGenerator(visibleCard2), String.valueOf(shopType), String.valueOf(2)));

                notifyObserverSingle(new NetworkMessage(MessageType.DRAW_CARD_UPDATE_SHOP_CARD_POOL,
                        argsGenerator(topDeckCard), String.valueOf(shopType), String.valueOf(0)));
                return supp;
            default:
                throw new RuntimeException("è stata richiesta un numero di carta non valido: "+numCard);
        }
    }

    /**
     * Check empty shop boolean.
     *
     * @return the boolean
     */
    public Boolean checkEmptyShop(){
        if(visibleCard1==null && visibleCard2==null && topDeckCard==null){
            return true;
        }
        else
            return false;
    }

    /**
     * Gets visible card 1.
     *
     * @return the visible card 1
     */
    public Card getVisibleCard1() {
        return visibleCard1;
    }

    /**
     * Gets visible card 2.
     *
     * @return the visible card 2
     */
    public Card getVisibleCard2() {
        return visibleCard2;
    }
}
