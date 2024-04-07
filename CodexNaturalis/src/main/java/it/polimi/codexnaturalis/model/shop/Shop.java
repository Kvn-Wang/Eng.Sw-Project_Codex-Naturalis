package it.polimi.codexnaturalis.model.shop;

import it.polimi.codexnaturalis.model.enumeration.ResourceType;
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
        Card travelingCard=null;
        if (topDeckCard == null) {
            switch (shopType){
                case RESOURCE:
                    travelingCard.createResourceCard();
                case OBJECTIVE:
                    travelingCard.createObjectiveCard();
                case STARTER:
                    travelingCard.createStarterCard();
            }
            switch (shopType){
                case RESOURCE:
                    topDeckCard.createResourceCard();
                case OBJECTIVE:
                    topDeckCard.createObjectiveCard();
                case STARTER:
                    topDeckCard.createStarterCard();
            }
        } else {
            travelingCard =topDeckCard;
            switch (shopType){
                case RESOURCE:
                    topDeckCard.createResourceCard();
                case OBJECTIVE:
                    topDeckCard.createObjectiveCard();
                case STARTER:
                    topDeckCard.createStarterCard();
            }

        }

        return travelingCard;
    }
    public Shop(ShopType type, String path){
        shopType = type;
        cardsFile = path;
    }
}
