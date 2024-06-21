package it.polimi.codexnaturalis.model.shop.card;

import it.polimi.codexnaturalis.model.enumeration.CardCorner;
import it.polimi.codexnaturalis.model.enumeration.CardType;
import it.polimi.codexnaturalis.model.enumeration.ConditionResourceType;
import it.polimi.codexnaturalis.model.enumeration.ResourceType;
import it.polimi.codexnaturalis.model.player.PlayerScoreResource;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public abstract class Card implements Serializable {
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

    /**
     * method that specifies that when an object has equal value then it's an equal object
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return png == card.png &&
                isBack == card.isBack &&
                Objects.equals(frontNorthResource, card.frontNorthResource) &&
                Objects.equals(frontSouthResource, card.frontSouthResource) &&
                Objects.equals(frontEastResource, card.frontEastResource) &&
                Objects.equals(frontWestResource, card.frontWestResource);
    }

    /**
     * method that specifies that when an object has equal value then it's an equal object
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(png, frontNorthResource, frontSouthResource, frontEastResource, frontWestResource, isBack);
    }
}
