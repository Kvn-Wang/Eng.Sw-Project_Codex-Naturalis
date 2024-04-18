package it.polimi.codexnaturalis.model.shop.card;

import it.polimi.codexnaturalis.model.enumeration.ConditionResourceType;
import it.polimi.codexnaturalis.model.enumeration.ResourceType;
import it.polimi.codexnaturalis.model.player.GamePlayerMap;
import it.polimi.codexnaturalis.model.player.PlayerScoreResource;

import java.util.ArrayList;

public class ObjectiveCard extends Card {
    protected ResourceType backCentralResource;
    protected ConditionResourceType pointPerConditionResource;
    protected int pointPerCondition;
    protected ResourceType[] conditionResources;

    public ObjectiveCard(int png, ResourceType frontNorthResource, ResourceType frontSouthResource, ResourceType frontEastResource, ResourceType frontWestResource,
                         ResourceType backCentralResource, ConditionResourceType pointPerConditionResource, int pointPerCondition, ResourceType [] conditionResource) {
        super(png, frontNorthResource, frontSouthResource, frontEastResource, frontWestResource);
        this.backCentralResource = backCentralResource;
        this.pointPerConditionResource = pointPerConditionResource;
        this.pointPerCondition = pointPerCondition;
        this.conditionResources = conditionResource;
    }

    public ResourceType getColor() {
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
    public ArrayList<ResourceType> getCardResources() {
        ArrayList<ResourceType> temp = new ArrayList<>();

        if(isBack) {
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
            if(pointPerConditionResource == ConditionResourceType.NONE) {
                return pointPerCondition;
            } else if(pointPerConditionResource == ConditionResourceType.OCCUPIEDSPACE) {
                return neightbouringCard * pointPerCondition;
            } else if(pointPerConditionResource == ConditionResourceType.INKWELL) {
                return (scoreCard.getScoreInkwell() * pointPerCondition);
            } else if(pointPerConditionResource == ConditionResourceType.MANUSCRIPT) {
                return (scoreCard.getScoreManuscript() * pointPerCondition);
            } else //pointPerConditionResource == ConditionResourceType.QUILL
                return (scoreCard.getScoreQuill() * pointPerCondition);
        }
    }


    @Override
    protected ResourceType getBackNorthResource() {
        return ResourceType.NONE;
    }

    @Override
    protected ResourceType getBackSouthResource() {
        return ResourceType.NONE;
    }

    @Override
    protected ResourceType getBackEastResource() {
        return ResourceType.NONE;
    }

    @Override
    protected ResourceType getBackWestResource() {
        return ResourceType.NONE;
    }
}