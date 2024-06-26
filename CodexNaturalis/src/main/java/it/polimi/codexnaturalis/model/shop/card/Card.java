package it.polimi.codexnaturalis.model.shop.card;

import it.polimi.codexnaturalis.model.enumeration.CardCorner;
import it.polimi.codexnaturalis.model.enumeration.CardType;
import it.polimi.codexnaturalis.model.enumeration.ConditionResourceType;
import it.polimi.codexnaturalis.model.enumeration.ResourceType;
import it.polimi.codexnaturalis.model.player.PlayerScoreResource;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

/**
 * The type Card.
 */
public abstract class Card implements Serializable {
    /**
     * The Png.
     */
    protected int png;
    /**
     * The Front north resource.
     */
    protected ResourceType frontNorthResource;
    /**
     * The Front south resource.
     */
    protected ResourceType frontSouthResource;
    /**
     * The Front east resource.
     */
    protected ResourceType frontEastResource;
    /**
     * The Front west resource.
     */
    protected ResourceType frontWestResource;
    /**
     * The Is back.
     */
    protected boolean isBack;
    /**
     * variable used to keep track of when a certain card has been placed
     */
    protected int placedOrder;

    /**
     * Instantiates a new Card.
     *
     * @param png                the png
     * @param frontNorthResource the front north resource
     * @param frontSouthResource the front south resource
     * @param frontEastResource  the front east resource
     * @param frontWestResource  the front west resource
     */
    public Card(int png, ResourceType frontNorthResource, ResourceType frontSouthResource, ResourceType frontEastResource, ResourceType frontWestResource) {
        this.png = png;
        this.frontNorthResource = frontNorthResource;
        this.frontSouthResource = frontSouthResource;
        this.frontEastResource = frontEastResource;
        this.frontWestResource = frontWestResource;
        this.isBack = false;
        placedOrder = 0;
    }

    /**
     * Gets card color.
     *
     * @return the card color
     */
    public abstract ResourceType getCardColor();

    /**
     * Check placeable card condition boolean.
     *
     * @param scoreCard the score card
     * @return the boolean
     */
    public abstract boolean checkPlaceableCardCondition(PlayerScoreResource scoreCard);

    /**
     * Gets card resources.
     *
     * @return the card resources
     */
    public abstract ArrayList<ResourceType> getCardResources();

    /**
     * Gets card points.
     *
     * @param scoreCard         the score card
     * @param neightbouringCard the neightbouring card
     * @return the card points
     */
    public abstract int getCardPoints(PlayerScoreResource scoreCard, int neightbouringCard);

    /**
     * Gets back north resource.
     *
     * @return the back north resource
     */
    public abstract ResourceType getBackNorthResource();

    /**
     * Gets back south resource.
     *
     * @return the back south resource
     */
    public abstract ResourceType getBackSouthResource();

    /**
     * Gets back east resource.
     *
     * @return the back east resource
     */
    public abstract ResourceType getBackEastResource();

    /**
     * Gets back west resource.
     *
     * @return the back west resource
     */
    public abstract ResourceType getBackWestResource();

    /**
     * Get back central resources resource type [ ].
     *
     * @return the resource type [ ]
     */
    public abstract ResourceType[] getBackCentralResources();

    /**
     * se non ha condizioni la carta, si restituisce ConditionResourceType.NONE
     *
     * @return the condition
     */
    public abstract ConditionResourceType getCondition();

    /**
     * return null if the card has no condition
     *
     * @return the resource type [ ]
     */
    public abstract ResourceType[] getPlaceableCardResources();

    /**
     * può essere il numero della risorsa che da punti,
     * oppure quella della condition card che da punti per ogni condizione soddisfatta
     *
     * @return the frontal number
     */
    public abstract int getFrontalNumber();

    /**
     * Gets card type.
     *
     * @return the card type
     */
    public abstract CardType getCardType();

    /**
     * Gets front north resource.
     *
     * @return the front north resource
     */
    public ResourceType getFrontNorthResource() {
        return frontNorthResource;
    }

    /**
     * Gets front south resource.
     *
     * @return the front south resource
     */
    public ResourceType getFrontSouthResource() {
        return frontSouthResource;
    }

    /**
     * Gets front east resource.
     *
     * @return the front east resource
     */
    public ResourceType getFrontEastResource() {
        return frontEastResource;
    }

    /**
     * Gets front west resource.
     *
     * @return the front west resource
     */
    public ResourceType getFrontWestResource() {
        return frontWestResource;
    }

    /**
     * Get card corner resource type.
     *
     * @param corner the corner
     * @return the resource type
     */
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

    /**
     * Gets png.
     *
     * @return the png
     */
    public int getPng() {
        return png;
    }

    /**
     * Gets is back.
     *
     * @return the is back
     */
    public boolean getIsBack() {
        return isBack;
    }

    /**
     * Sets is back.
     *
     * @param back the back
     */
    public void setIsBack(boolean back) {
        isBack = back;
    }

    /**
     * Check valid resource boolean.
     *
     * @param resourceType the resource type
     * @return the boolean
     */
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

    public int getPlacedOrder() {
        return placedOrder;
    }

    public void setPlacedOrder(int placedOrder) {
        this.placedOrder = placedOrder;
    }
}
