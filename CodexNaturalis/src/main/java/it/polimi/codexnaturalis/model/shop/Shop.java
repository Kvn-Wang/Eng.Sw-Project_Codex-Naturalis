package it.polimi.codexnaturalis.model.shop;

import it.polimi.codexnaturalis.model.shop.card.Card;
import it.polimi.codexnaturalis.model.enumeration.ShopType;

public class Shop {
    public ShopType shopType;
    protected Card topDeckCard;
    protected String starterCardsFile;
    protected String ResorceCardsFile;
    protected String objectiveCardsFile;

    private void shuffle(){
        System.out.printf("shuffled");
    }
    public Card drawFromDeck(){
        System.out.printf("from deck");
        return topDeckCard;
    }
}
