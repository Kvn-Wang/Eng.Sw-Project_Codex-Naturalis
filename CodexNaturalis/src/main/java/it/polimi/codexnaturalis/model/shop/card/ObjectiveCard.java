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

    public ResourceType[] getConditionResources() {
        System.out.printf("conditionResource");
        return conditionResources;
    }

    public void setConditionResources(ResourceType[] conditionResources) {
        this.conditionResources = conditionResources;
    }

    public ResourceType getBackCentralResource() {
        System.out.printf("backCentralResource");
        return backCentralResource;
    }

    public void setBackCentralResource(ResourceType backCentralResource) {
        this.backCentralResource = backCentralResource;
    }

    public ConditionResourceType getPointPerConditionResource() {
        System.out.printf("pointPerConditionResource");
        return pointPerConditionResource;
    }

    public void setPointPerConditionResource(ConditionResourceType pointPerConditionResource) {
        this.pointPerConditionResource = pointPerConditionResource;
    }

    public int getPointPerCondition() {
        System.out.printf("pointPerCondition");
        return pointPerCondition;
    }

    public void setPointPerCondition(int pointPerCondition) {
        this.pointPerCondition = pointPerCondition;
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
    public ArrayList<ResourceType> getBackResources() {
        ArrayList<ResourceType> temp = new ArrayList<>();
        temp.add(backCentralResource);
        return temp;
    }

    @Override
    public int getFrontCardPointCondition(PlayerScoreResource scoreCard, int neightbouringCard) {
        if(isBack) {
            return 0;
        } else {
            if(pointPerConditionResource == ConditionResourceType.NONE) {
                return pointPerCondition;
            } else if(pointPerConditionResource == ConditionResourceType.OCCUPIEDSPACE) {
                return neightbouringCard * pointPerCondition;
            } else if(pointPerConditionResource == ConditionResourceType.INKWELL) {
                return scoreCard.getScoreInkwell();
            } else if(pointPerConditionResource == ConditionResourceType.MANUSCRIPT) {
                return scoreCard.getScoreManuscript();
            } else //pointPerConditionResource == ConditionResourceType.QUILL
                return scoreCard.getScoreQuill();
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