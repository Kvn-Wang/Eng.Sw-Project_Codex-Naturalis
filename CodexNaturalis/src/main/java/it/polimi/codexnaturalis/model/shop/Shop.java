package it.polimi.codexnaturalis.model.shop;

import it.polimi.codexnaturalis.model.shop.card.Card;
import it.polimi.codexnaturalis.model.enumeration.ShopType;

public class Shop {
    public ShopType shopType;
    public Card topDeckCard;
    private String originalCardsFile;
    private String cardsFile;

    private void shuffle(){
        System.out.printf("shuffled");
    }
    public Card drawFromDeck(){
        Card drawnCard = null;
        System.out.printf("card drew from deck");
        return drawnCard;
    }
}
