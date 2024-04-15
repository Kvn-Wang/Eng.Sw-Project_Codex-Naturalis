package it.polimi.codexnaturalis.model.shop.card;

import it.polimi.codexnaturalis.model.enumeration.ResourceType;

public class ResourceCard extends Card{
    protected ResourceType backCentralResource;
    protected int frontCardPoint;

    public ResourceCard(int png, ResourceType frontNorthResource, ResourceType frontSouthResource, ResourceType frontEastResource, ResourceType frontWestResource,
                        ResourceType backCentralResource, int frontCardPoint) {
        super(png, frontNorthResource, frontSouthResource, frontEastResource, frontWestResource);
        this.backCentralResource = backCentralResource;
        this.frontCardPoint = frontCardPoint;
    }

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
