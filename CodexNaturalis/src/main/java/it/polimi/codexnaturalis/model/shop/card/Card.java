package it.polimi.codexnaturalis.model.shop.card;

import it.polimi.codexnaturalis.model.enumeration.CardCorner;
import it.polimi.codexnaturalis.model.enumeration.CardType;
import it.polimi.codexnaturalis.model.enumeration.ConditionResourceType;
import it.polimi.codexnaturalis.model.enumeration.ResourceType;
import it.polimi.codexnaturalis.model.player.PlayerScoreResource;

import java.util.ArrayList;

public abstract class Card {
    protected boolean isNwCovered;
    protected boolean isNeCovered;
    protected boolean isSeCovered;
    protected boolean isSwCovered;
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
    public abstract ResourceType getCardColor();
    public abstract boolean checkPlaceableCardCondition(PlayerScoreResource scoreCard);
    public abstract ArrayList<ResourceType> getCardResources();
    public abstract int getCardPoints(PlayerScoreResource scoreCard, int neightbouringCard);
    public abstract ResourceType getBackNorthResource();
    public abstract ResourceType getBackSouthResource();
    public abstract ResourceType getBackEastResource();
    public abstract ResourceType getBackWestResource();
    public abstract ResourceType[] getBackCentralResources();
    // se non ha condizioni la carta, si restituisce ConditionResourceType.NONE
    public abstract ConditionResourceType getCondition();
    public abstract ResourceType[] getPlaceableCardResources();
    //può essere il numero della risorsa che da punti, oppure quella della condition card che da punti per ogni condizione soddisfatta
    public abstract int getFrontalNumber();
    public abstract CardType getCardType();

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
        if(resourceType != ResourceType.NONE && resourceType != ResourceType.UNASSIGNABLE) {
            return true;
        } else {
            return false;
        }
    }

    public boolean getIsNwCovered() {
        return isNwCovered;
    }

    public boolean getIsNeCovered() {
        return isNeCovered;
    }

    public boolean getIsSeCovered() {
        return isSeCovered;
    }

    public boolean getIsSwCovered() {
        return isSwCovered;
    }

    public void setIsNwCovered(boolean isNwCovered) {
        isNwCovered = isNwCovered;
    }

    public void setIsNeCovered(boolean isNeCovered) {
        isNeCovered = isNeCovered;
    }

    public void setIsSeCovered(boolean isSeCovered) {
        isSeCovered = isSeCovered;
    }

    public void setIsSwCovered(boolean isSwCovered) {
        isSwCovered = isSwCovered;
    }
}
