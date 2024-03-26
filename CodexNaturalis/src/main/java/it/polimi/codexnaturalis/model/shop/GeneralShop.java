package it.polimi.codexnaturalis.model.shop;

import it.polimi.codexnaturalis.model.shop.card.Card;

public class GeneralShop extends Shop {
    public Card visibleCard1;
    public Card visibleCard2;
    public Card drawFromShopPlayer(){
        Card drawnCard;
        System.out.printf("card drew from visible shop");
        return drawnCard;
    }
}
