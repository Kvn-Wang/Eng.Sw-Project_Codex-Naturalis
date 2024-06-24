package it.polimi.codexnaturalis.model.shop.card;

import it.polimi.codexnaturalis.model.enumeration.CardType;
import it.polimi.codexnaturalis.model.enumeration.ConditionResourceType;
import it.polimi.codexnaturalis.model.enumeration.ResourceType;
import it.polimi.codexnaturalis.model.player.GamePlayerMap;
import it.polimi.codexnaturalis.model.player.PlayerScoreResource;

import java.util.ArrayList;

/**
 * The type Objective card.
 */
public class ObjectiveCard extends Card {
    /**
     * The Back central resource.
     */
    protected ResourceType backCentralResource;
    /**
     * The Point per condition resource.
     * rappresenta il sopra
     */
    protected ConditionResourceType pointPerConditionResource;
    /**
     * The Point per condition.
     */
    protected int pointPerCondition;
    /**
     * The Condition resources.
     * rappresenta il sotto
     */
    protected ResourceType[] conditionResources;

    /**
     * Instantiates a new Objective card.
     *
     * @param png                       the png
     * @param frontNorthResource        the front north resource
     * @param frontSouthResource        the front south resource
     * @param frontEastResource         the front east resource
     * @param frontWestResource         the front west resource
     * @param backCentralResource       the back central resource
     * @param pointPerConditionResource the point per condition resource
     * @param pointPerCondition         the point per condition
     * @param conditionResource         the condition resource
     */
    public ObjectiveCard(int png, ResourceType frontNorthResource, ResourceType frontSouthResource, ResourceType frontEastResource, ResourceType frontWestResource,
                         ResourceType backCentralResource, ConditionResourceType pointPerConditionResource, int pointPerCondition, ResourceType [] conditionResource) {
        super(png, frontNorthResource, frontSouthResource, frontEastResource, frontWestResource);
        this.backCentralResource = backCentralResource;
        this.pointPerConditionResource = pointPerConditionResource;
        this.pointPerCondition = pointPerCondition;
        this.conditionResources = conditionResource;
    }

    @Override
    public ResourceType getCardColor() {
        return backCentralResource;
    }

    @Override
    public boolean checkPlaceableCardCondition(PlayerScoreResource scoreCard) {
        PlayerScoreResource copy = new PlayerScoreResource(scoreCard);

        for(ResourceType element : conditionResources) {
            if(!copy.substractScore(element)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public CardType getCardType(){
        return CardType.OBJECTIVE;
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
    public int getCardPoints(PlayerScoreResource scoreCard, int neighbouringCard) {
        if(isBack || neighbouringCard <0 || neighbouringCard >4) {
            return 0;
        } else {
            if(pointPerConditionResource == ConditionResourceType.NONE) {
                return pointPerCondition;
            } else if(pointPerConditionResource == ConditionResourceType.OCCUPIEDSPACE) {
                return neighbouringCard * pointPerCondition;
            } else if(pointPerConditionResource == ConditionResourceType.INKWELL) {
                return (scoreCard.getScoreInkwell() * pointPerCondition);
            } else if(pointPerConditionResource == ConditionResourceType.MANUSCRIPT) {
                return (scoreCard.getScoreManuscript() * pointPerCondition);
            } else if(pointPerConditionResource == ConditionResourceType.QUILL){
                return (scoreCard.getScoreQuill() * pointPerCondition);
            } else return 0;
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
        return pointPerConditionResource;
    }

    @Override
    public ResourceType[] getPlaceableCardResources() {
        return conditionResources;
    }

    @Override
    public int getFrontalNumber() {
        return pointPerCondition;
    }
}