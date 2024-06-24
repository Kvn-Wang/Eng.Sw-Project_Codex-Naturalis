package it.polimi.codexnaturalis.model.shop.card;

import it.polimi.codexnaturalis.model.enumeration.CardType;
import it.polimi.codexnaturalis.model.enumeration.ConditionResourceType;
import it.polimi.codexnaturalis.model.enumeration.ResourceType;
import it.polimi.codexnaturalis.model.player.GamePlayerMap;
import it.polimi.codexnaturalis.model.player.PlayerScoreResource;

import java.util.ArrayList;

/**
 * The type Resource card.
 */
public class ResourceCard extends Card{
    /**
     * The Back central resource.
     */
    protected ResourceType backCentralResource;
    /**
     * The Front card point.
     */
    protected int frontCardPoint;

    /**
     * Instantiates a new Resource card.
     *
     * @param png                 the png
     * @param frontNorthResource  the front north resource
     * @param frontSouthResource  the front south resource
     * @param frontEastResource   the front east resource
     * @param frontWestResource   the front west resource
     * @param backCentralResource the back central resource
     * @param frontCardPoint      the front card point
     */
    public ResourceCard(int png, ResourceType frontNorthResource, ResourceType frontSouthResource, ResourceType frontEastResource, ResourceType frontWestResource,
                        ResourceType backCentralResource, int frontCardPoint) {
        super(png, frontNorthResource, frontSouthResource, frontEastResource, frontWestResource);
        this.backCentralResource = backCentralResource;
        this.frontCardPoint = frontCardPoint;
    }

    /**
     * Gets front card point.
     *
     * @return the front card point
     */
    public int getFrontCardPoint() {
        return frontCardPoint;
    }

    @Override
    public ResourceType getCardColor() {
        return backCentralResource;
    }

    @Override
    public boolean checkPlaceableCardCondition(PlayerScoreResource scoreCard) {
        return true;
    }

    @Override
    public ArrayList<ResourceType> getCardResources() {
        ArrayList<ResourceType> temp = new ArrayList<>();

        if(!isBack) {
            if(checkValidResource(frontNorthResource)) {
                temp.add(frontNorthResource);
            }
            if(checkValidResource(frontSouthResource)) {
                temp.add(frontSouthResource);
            }
            if(checkValidResource(frontEastResource)) {
                temp.add(frontEastResource);
            }
            if(checkValidResource(frontWestResource)) {
                temp.add(frontWestResource);
            }
        } else {
            if(checkValidResource(backCentralResource)) {
                temp.add(backCentralResource);
            }
        }
        return temp;
    }

    @Override
    public int getCardPoints(PlayerScoreResource scoreCard, int neightbouringCard) {
        if(isBack) {
            return 0;
        } else {
            return frontCardPoint;
        }
    }

    @Override
    public ResourceType getBackNorthResource() {
        return ResourceType.NONE;
    }

    @Override
    public ResourceType getBackSouthResource() {
        return ResourceType.NONE;
    }

    @Override
    public ResourceType getBackEastResource() {
        return ResourceType.NONE;
    }

    @Override
    public ResourceType getBackWestResource() {
        return ResourceType.NONE;
    }

    @Override
    public ResourceType[] getBackCentralResources() {
        return new ResourceType[]{backCentralResource};
    }

    @Override
    public ConditionResourceType getCondition() {
        return ConditionResourceType.NONE;
    }

    @Override
    public ResourceType[] getPlaceableCardResources() {
        return new ResourceType[0];
    }

    @Override
    public int getFrontalNumber() {
        return frontCardPoint;
    }

    @Override
    public CardType getCardType() {
        return CardType.RESOURCE;
    }
}
