package it.polimi.codexnaturalis.model.shop.card;

import com.google.gson.annotations.SerializedName;
import it.polimi.codexnaturalis.model.enumeration.CardCorner;
import it.polimi.codexnaturalis.model.enumeration.CardType;
import it.polimi.codexnaturalis.model.enumeration.ResourceType;
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
    public abstract ArrayList<ResourceType> getCardResources();
    public abstract int getCardPoints(PlayerScoreResource scoreCard, int neightbouringCard);
    protected abstract ResourceType getBackNorthResource();
    protected abstract ResourceType getBackSouthResource();
    protected abstract ResourceType getBackEastResource();
    protected abstract ResourceType getBackWestResource();
    public abstract CardType getCardType();

    //NB: ritorna NONE se il corner è utilizzabile per piazzare carte, null altrimenti
    public ResourceType getCardCorner(CardCorner corner){
        switch(corner){
            case NORTH:
                if(!isBack) {
                    return frontNorthResource;
                }else{
                    return getBackNorthResource();
                }
            case SOUTH:
                if(!isBack) {
                    return frontSouthResource;
                } else {
                    return getBackSouthResource();
                }
            case EAST:
                if(!isBack) {
                    return frontEastResource;
                } else {
                    return getBackEastResource();
                }
            case WEST:
                if(!isBack) {
                    return frontWestResource;
                } else {
                    return getBackWestResource();
                }
        }
        return null;
    }

    public int getPng() {
        return png;
    }

    public boolean getIsBack() {
        return isBack;
    }

    public void setIsBack(boolean back) {
        isBack = back;
    }

    public boolean checkValidResource(ResourceType resourceType) {
        if(resourceType != ResourceType.NONE && resourceType != null) {
            return true;
        } else {
            return false;
        }
    }
}
