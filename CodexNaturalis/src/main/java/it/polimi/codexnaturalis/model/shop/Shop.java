package it.polimi.codexnaturalis.model.shop;

import it.polimi.codexnaturalis.model.enumeration.ResourceType;
import it.polimi.codexnaturalis.model.shop.card.Card;
import it.polimi.codexnaturalis.model.enumeration.ShopType;

public class Shop {
    public final ShopType shopType;
    protected final String cardsFile;
    protected Card topDeckCard;
    protected Card travelingCard;

    private void shuffle(){
        System.out.printf("shuffled");
    }
    public Card drawFromDeck(){
        System.out.printf("from deck");
        if (topDeckCard == null) {
            switch (this.shopType){
                case RESOURCE:
                    initiateResourceCard(travelingCard);
                case OBJECTIVE:
                    initiateObjectiveCard(travelingCard);
                case STARTER:
                    initiateStarterCard(travelingCard);
            }
            switch (this.shopType){
                case RESOURCE:
                    initiateResourceCard(topDeckCard);
                case OBJECTIVE:
                    initiateObjectiveCard(topDeckCard);
                case STARTER:
                    initiateStarterCard(topDeckCard);
            }
        } else {
            travelingCard =topDeckCard;
            switch (this.shopType){
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

    protected Card initiateResourceCard(Card DeckCard) {
        DeckCard.createResourceCard();

        return DeckCard;
    }
    protected Card initiateObjectiveCard(Card DeckCard) {
        DeckCard.createObjectiveCard();

        return DeckCard;
    }
    protected Card initiateStarterCard(Card DeckCard) {
        DeckCard.createStarterCard();

        return DeckCard;
    }
    public Shop(ShopType type, String path){
        shopType = type;
        cardsFile = path;
    }
}
