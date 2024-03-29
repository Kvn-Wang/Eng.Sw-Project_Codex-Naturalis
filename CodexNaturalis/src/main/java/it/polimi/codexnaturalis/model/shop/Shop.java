package it.polimi.codexnaturalis.model.shop;

import it.polimi.codexnaturalis.model.shop.card.Card;
import it.polimi.codexnaturalis.model.enumeration.ShopType;

public class Shop {
    public final ShopType shopType;
    protected final String cardsFile;
    protected Card topDeckCard;

    private void shuffle(){
        System.out.printf("shuffled");
    }
    public Card drawFromDeck(){
        System.out.printf("from deck");
        return topDeckCard;
    }
    public Shop(ShopType type, String path){
        shopType = type;
        cardsFile = path;
    }
}
