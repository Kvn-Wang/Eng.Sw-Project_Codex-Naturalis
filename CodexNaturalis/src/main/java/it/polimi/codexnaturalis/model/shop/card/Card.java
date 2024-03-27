package it.polimi.codexnaturalis.model.shop.card;

import it.polimi.codexnaturalis.model.enumeration.ConditionResourceType;
import it.polimi.codexnaturalis.model.enumeration.ResourceType;

public class Card {
    protected String png;
    protected ResourceType frontNorthResource;
    protected ResourceType frontSouthResource;
    protected ResourceType frontEastResource;
    protected ResourceType frontWestResource;
    protected boolean isBack;

    protected boolean usedForCheckRule;

    public boolean isBack() {
        return isBack;
    }

    public void setBack(boolean back) {
        isBack = back;
    }

    public ResourceType getFrontNorthResource() {
        return frontNorthResource;
    }

    public void setFrontNorthResource(ResourceType frontNorthResource) {
        this.frontNorthResource = frontNorthResource;
    }

    public ResourceType getFrontSouthResource() {
        return frontSouthResource;
    }

    public void setFrontSouthResource(ResourceType frontSouthResource) {
        this.frontSouthResource = frontSouthResource;
    }

    public ResourceType getFrontEastResource() {
        return frontEastResource;
    }

    public void setFrontEastResource(ResourceType frontEastResource) {
        this.frontEastResource = frontEastResource;
    }

    public ResourceType getFrontWestResource() {
        return frontWestResource;
    }

    public void setFrontWestResource(ResourceType frontWestResource) {
        this.frontWestResource = frontWestResource;
    }

    public String getPng() {
        return png;
    }

    public void setPng(String png) {
        this.png = png;
    }

    public boolean isUsedForCheckRule() {
        return usedForCheckRule;
    }

    public void setUsedForCheckRule(boolean usedForCheckRule) {
        this.usedForCheckRule = usedForCheckRule;
    }

    class resourceCard extends Card{
        protected ResourceType backCentralResource;
        protected int frontCardPoint;

        public ResourceType getBackCentralResource() {
            return backCentralResource;
        }

        public void setBackCentralResource(ResourceType backCentralResource) {
            this.backCentralResource = backCentralResource;
        }

        public int getFrontCardPoint() {
            return frontCardPoint;
        }

        public void setFrontCardPoint(int frontCardPoint) {
            this.frontCardPoint = frontCardPoint;
        }
    }

    class objectiveCard extends Card{
        protected ResourceType backCentralResource;
        protected ConditionResourceType pointPerConditionResource;
        protected int pointPerCondition;
        protected ResourceType[] conditionResource;

        public ResourceType[] getConditionResource() {
            return conditionResource;
        }

        public void setConditionResource(ResourceType[] conditionResource) {
            this.conditionResource = conditionResource;
        }

        public ResourceType getBackCentralResource() {
            return backCentralResource;
        }

        public void setBackCentralResource(ResourceType backCentralResource) {
            this.backCentralResource = backCentralResource;
        }

        public ConditionResourceType getPointPerConditionResource() {
            return pointPerConditionResource;
        }

        public void setPointPerConditionResource(ConditionResourceType pointPerConditionResource) {
            this.pointPerConditionResource = pointPerConditionResource;
        }

        public int getPointPerCondition() {
            return pointPerCondition;
        }

        public void setPointPerCondition(int pointPerCondition) {
            this.pointPerCondition = pointPerCondition;
        }
    }
    class starterCard extends Card{
        ResourceType[] backCentralResource;
        protected ResourceType backNorthResource;
        protected ResourceType backSouthResource;
        protected ResourceType backEastResource;
        protected ResourceType backWestResource;

        public ResourceType[] getBackCentralResource() {
            return backCentralResource;
        }

        public void setBackCentralResource(ResourceType[] backCentralResource) {
            this.backCentralResource = backCentralResource;
        }

        public ResourceType getBackNorthResource() {
            return backNorthResource;
        }

        public void setBackNorthResource(ResourceType backNorthResource) {
            this.backNorthResource = backNorthResource;
        }

        public ResourceType getBackSouthResource() {
            return backSouthResource;
        }

        public void setBackSouthResource(ResourceType backSouthResource) {
            this.backSouthResource = backSouthResource;
        }

        public ResourceType getBackEastResource() {
            return backEastResource;
        }

        public void setBackEastResource(ResourceType backEastResource) {
            this.backEastResource = backEastResource;
        }

        public ResourceType getBackWestResource() {
            return backWestResource;
        }

        public void setBackWestResource(ResourceType backWestResource) {
            this.backWestResource = backWestResource;
        }
    }
}
