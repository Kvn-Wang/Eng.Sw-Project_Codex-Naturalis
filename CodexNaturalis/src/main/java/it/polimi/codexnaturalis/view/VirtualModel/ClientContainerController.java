package it.polimi.codexnaturalis.view.VirtualModel;

import it.polimi.codexnaturalis.model.player.Hand;
import it.polimi.codexnaturalis.model.shop.card.Card;

public interface ClientContainerController {
    void setPlayerMap(int x, int y, Card card);
    Card[][] getPlayerMap();
    void setHand(Hand hand);
    Hand getHand();
}
