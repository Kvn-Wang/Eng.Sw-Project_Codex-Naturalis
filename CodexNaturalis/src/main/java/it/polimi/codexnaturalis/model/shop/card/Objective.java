package it.polimi.codexnaturalis.model.shop.card;

import it.polimi.codexnaturalis.model.enumeration.ConditionResourceType;
import it.polimi.codexnaturalis.model.enumeration.ResourceType;

class objectiveCard extends Card {
    protected ResourceType backCentralResource;
    protected ConditionResourceType pointPerConditionResource;
    protected int pointPerCondition;
    protected ResourceType[] conditionResource;

    public ResourceType[] getConditionResource() {
        System.out.printf("conditionResource");
        return conditionResource;
    }

    public void setConditionResource(ResourceType[] conditionResource) {
        this.conditionResource = conditionResource;
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
}