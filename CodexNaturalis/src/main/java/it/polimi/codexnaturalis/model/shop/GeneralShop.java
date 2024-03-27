package it.polimi.codexnaturalis.model.shop;

import it.polimi.codexnaturalis.model.shop.card.Card;

public class GeneralShop extends Shop {
    private Card visibleCard1;
    private Card visibleCard2;
    public Card drawFromShopPlayer(int numCard){
        System.out.printf("drawing card");
        switch(numCard) {
            case 0:
                return super.drawFromDeck();
            case 1:
                return visibleCard1;
            case 2:
                return visibleCard2;
        }
        return null;
    }
}
