package it.polimi.codexnaturalis;

public class Shop {
    public ShopType shopType;
    public Card topDeckCard;
    private String originalCardsFile;
    private String cardsFile;

    private void shuffle(){
        System.out.printf("shuffled");
    }
    public Card drawFromDeck(){
        Card drawnCard;
        System.out.printf("card drew from deck");
        return drawnCard;
    }
}
