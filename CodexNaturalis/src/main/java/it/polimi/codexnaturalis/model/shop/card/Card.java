package it.polimi.codexnaturalis.model.shop.card;

import it.polimi.codexnaturalis.model.enumeration.ResourceType;
import it.polimi.codexnaturalis.model.player.GamePlayerMap;
import it.polimi.codexnaturalis.model.player.PlayerScoreResource;

import java.util.ArrayList;

public abstract class Card {
    protected int png;
    protected ResourceType frontNorthResource;
    protected ResourceType frontSouthResource;
    protected ResourceType frontEastResource;
    protected ResourceType frontWestResource;
    protected boolean isBack;

    public Card(int png, ResourceType frontNorthResource, ResourceType frontSouthResource, ResourceType frontEastResource, ResourceType frontWestResource) {
        this.png = png;
        this.frontNorthResource = frontNorthResource;
        this.frontSouthResource = frontSouthResource;
        this.frontEastResource = frontEastResource;
        this.frontWestResource = frontWestResource;
        this.isBack = false;
    }
    public abstract ResourceType getColor();
    public abstract boolean checkPlaceableCardCondition(PlayerScoreResource scoreCard);
    public abstract ArrayList<ResourceType> getBackResources();
    public abstract int getFrontCardPointCondition(PlayerScoreResource scoreCard, GamePlayerMap playerMap);

    public boolean getIsBack() {
        return isBack;
    }

    public ResourceType getFrontNorthResource() {
        return frontNorthResource;
    }

    public ResourceType getFrontSouthResource() {
        return frontSouthResource;
    }
    public ResourceType getFrontEastResource() {
        return frontEastResource;
    }
    public ResourceType getFrontWestResource() {
        return frontWestResource;
    }
    public int getPng() {
        return png;
    }
}
