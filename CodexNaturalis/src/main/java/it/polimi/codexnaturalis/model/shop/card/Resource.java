package it.polimi.codexnaturalis.model.shop.card;

import it.polimi.codexnaturalis.model.enumeration.ResourceType;

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

    public ResourceType getColor() {
        return backCentralResource;
    }
}
