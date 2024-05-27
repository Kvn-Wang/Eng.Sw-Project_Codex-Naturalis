package it.polimi.codexnaturalis.view.VirtualModel;

import it.polimi.codexnaturalis.model.shop.card.Card;
import it.polimi.codexnaturalis.network.util.PlayerInfo;

import java.util.ArrayList;

public class ClientContainer implements ClientContainerController {
    private ArrayList<PlayerInfo> otherPlayerList;
    private Card[][] personalGameMap;
    private Card[] personalHand;

    public ClientContainer(ArrayList<PlayerInfo> otherPlayerList) {
        this.otherPlayerList = otherPlayerList;
    }

    @Override
    public void setPlayerMap(int x, int y, Card card) {
        personalGameMap[x][y] = card;
    }

    @Override
    public Card[][] getPlayerMap() {
        return personalGameMap;
    }

    @Override
    public void setHand(Card[] hand) {
        personalHand = hand;
    }

    @Override
    public Card[] getHand() {
        return personalHand;
    }
}
