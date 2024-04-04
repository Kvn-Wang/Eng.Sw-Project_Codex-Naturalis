package it.polimi.codexnaturalis.model.shop.card;

import it.polimi.codexnaturalis.model.enumeration.ConditionResourceType;
import it.polimi.codexnaturalis.model.enumeration.ResourceType;

public abstract class Card {
    protected String png;
    protected ResourceType frontNorthResource;
    protected ResourceType frontSouthResource;
    protected ResourceType frontEastResource;
    protected ResourceType frontWestResource;
    protected boolean isBack;

    protected boolean usedForCheckRule;

    public boolean getIsBack() {
        System.out.printf("isBack");
        return isBack;
    }

    public void setBack(boolean back) {
        isBack = back;
    }

    public ResourceType getFrontNorthResource() {
        System.out.printf("frontNorthResource");
        return frontNorthResource;
    }

    public void setFrontNorthResource(ResourceType frontNorthResource) {
        this.frontNorthResource = frontNorthResource;
    }

    public ResourceType getFrontSouthResource() {
        System.out.printf("frontSouthResource");
        return frontSouthResource;
    }

    public void setFrontSouthResource(ResourceType frontSouthResource) {
        this.frontSouthResource = frontSouthResource;
    }

    public ResourceType getFrontEastResource() {
        System.out.printf("frontEastResource");
        return frontEastResource;
    }

    public void setFrontEastResource(ResourceType frontEastResource) {
        this.frontEastResource = frontEastResource;
    }

    public ResourceType getFrontWestResource() {
        System.out.printf("frontWestResource");
        return frontWestResource;
    }

    public void setFrontWestResource(ResourceType frontWestResource) {
        this.frontWestResource = frontWestResource;
    }

    public String getPng() {
        System.out.printf("Png");
        return png;
    }

    public void setPng(String png) {
        this.png = png;
    }

    public boolean getIsUsedForCheckRule() {
        System.out.printf("isUsedForCheckRule");
        return usedForCheckRule;
    }

    public void setUsedForCheckRule(boolean usedForCheckRule) {
        this.usedForCheckRule = usedForCheckRule;
    }

    class resourceCard extends Card{
        protected ResourceType backCentralResource;
        protected int frontCardPoint;

        public ResourceType getBackCentralResource() {
            System.out.printf("backCentralResource");
            return backCentralResource;
        }

        public void setBackCentralResource(ResourceType backCentralResource) {
            this.backCentralResource = backCentralResource;
        }

        public int getFrontCardPoint() {
            System.out.printf("frontCardPoint");
            return frontCardPoint;
        }

        public void setFrontCardPoint(int frontCardPoint) {
            this.frontCardPoint = frontCardPoint;
        }
    }

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
    }
    class starterCard extends Card{
        ResourceType[] backCentralResource;
        protected ResourceType backNorthResource;
        protected ResourceType backSouthResource;
        protected ResourceType backEastResource;
        protected ResourceType backWestResource;

        public ResourceType[] getBackCentralResource() {
            System.out.printf("backCentralResource");
            return backCentralResource;
        }

        public void setBackCentralResource(ResourceType[] backCentralResource) {
            this.backCentralResource = backCentralResource;
        }

        public ResourceType getBackNorthResource() {
            System.out.printf("backNorthResource");
            return backNorthResource;
        }

        public void setBackNorthResource(ResourceType backNorthResource) {
            this.backNorthResource = backNorthResource;
        }

        public ResourceType getBackSouthResource() {
            System.out.printf("backSouthResource");
            return backSouthResource;
        }

        public void setBackSouthResource(ResourceType backSouthResource) {
            this.backSouthResource = backSouthResource;
        }

        public ResourceType getBackEastResource() {
            System.out.printf("backEastResource");
            return backEastResource;
        }

        public void setBackEastResource(ResourceType backEastResource) {
            this.backEastResource = backEastResource;
        }

        public ResourceType getBackWestResource() {
            System.out.printf("backWestResource");
            return backWestResource;
        }

        public void setBackWestResource(ResourceType backWestResource) {
            this.backWestResource = backWestResource;
        }
    }
}
