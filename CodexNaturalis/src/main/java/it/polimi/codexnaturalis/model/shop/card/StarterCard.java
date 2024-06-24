package it.polimi.codexnaturalis.model.shop.card;

import it.polimi.codexnaturalis.model.enumeration.CardType;
import it.polimi.codexnaturalis.model.enumeration.ConditionResourceType;
import it.polimi.codexnaturalis.model.enumeration.ResourceType;
import it.polimi.codexnaturalis.model.player.GamePlayerMap;
import it.polimi.codexnaturalis.model.player.PlayerScoreResource;

import java.util.ArrayList;

/**
 * The type Starter card.
 */
public class StarterCard extends Card{
    /**
     * The Back central resource.
     */
    ResourceType[] backCentralResource;
    /**
     * The Back north resource.
     */
    protected ResourceType backNorthResource;
    /**
     * The Back south resource.
     */
    protected ResourceType backSouthResource;
    /**
     * The Back east resource.
     */
    protected ResourceType backEastResource;
    /**
     * The Back west resource.
     */
    protected ResourceType backWestResource;

    /**
     * Instantiates a new Starter card.
     *
     * @param png                 the png
     * @param frontNorthResource  the front north resource
     * @param frontSouthResource  the front south resource
     * @param frontEastResource   the front east resource
     * @param frontWestResource   the front west resource
     * @param backCentralResource the back central resource
     * @param backNorthResource   the back north resource
     * @param backSouthResource   the back south resource
     * @param backEastResource    the back east resource
     * @param backWestResource    the back west resource
     */
    public StarterCard(int png, ResourceType frontNorthResource, ResourceType frontSouthResource, ResourceType frontEastResource, ResourceType frontWestResource,
                       ResourceType [] backCentralResource, ResourceType backNorthResource, ResourceType backSouthResource, ResourceType backEastResource, ResourceType backWestResource) {
        super(png, frontNorthResource, frontSouthResource, frontEastResource, frontWestResource);
        this.backCentralResource = backCentralResource;
        this.backNorthResource = backNorthResource;
        this.backSouthResource = backSouthResource;
        this.backEastResource = backEastResource;
        this.backWestResource = backWestResource;
    }

    @Override
    public ResourceType getCardColor() {
        return ResourceType.NONE;
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
            for(ResourceType element : backCentralResource) {
                if(checkValidResource(element)) {
                    temp.add(element);
                }
            }
        } else {
            if(checkValidResource(backNorthResource)) {
                temp.add(backNorthResource);
            }
            if(checkValidResource(backSouthResource)) {
                temp.add(backSouthResource);
            }
            if(checkValidResource(backWestResource)) {
                temp.add(backWestResource);
            }
            if(checkValidResource(backEastResource)) {
                temp.add(backEastResource);
            }
        }
        return temp;
    }

    @Override
    public int getCardPoints(PlayerScoreResource scoreCard, int neightbouringCard) {
        return 0;
    }

    @Override
    public ResourceType getBackNorthResource() {
        return backNorthResource;
    }

    @Override
    public ResourceType getBackSouthResource() {
        return backSouthResource;
    }

    @Override
    public ResourceType getBackEastResource() {
        return backEastResource;
    }

    @Override
    public ResourceType getBackWestResource() {
        return backWestResource;
    }

    @Override
    public ResourceType[] getBackCentralResources() {
        return backCentralResource;
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
        return 0;
    }

    @Override
    public CardType getCardType() {
        return CardType.STARTER;
    }
}
