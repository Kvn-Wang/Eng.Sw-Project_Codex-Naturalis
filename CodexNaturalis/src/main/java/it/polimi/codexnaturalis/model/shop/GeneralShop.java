package it.polimi.codexnaturalis.model.shop;

import it.polimi.codexnaturalis.model.enumeration.ShopType;
import it.polimi.codexnaturalis.model.shop.card.Card;

public class GeneralShop extends Shop {
    private Card visibleCard1;
    private Card visibleCard2;

    public GeneralShop(ShopType type) {
        super(type);
        visibleCard1 = drawTopDeckCard();
        visibleCard2 = drawTopDeckCard();
    }

    public Card drawFromShopPlayer(int numCard){
        System.out.printf("drawing card");
        Card supp;

        switch(numCard) {
            case 1:
                supp = visibleCard1;
                visibleCard1 = drawTopDeckCard();
                return supp;
            case 2:
                supp = visibleCard2;
                visibleCard2 = drawTopDeckCard();
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
