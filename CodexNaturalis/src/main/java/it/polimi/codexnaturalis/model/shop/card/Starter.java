package it.polimi.codexnaturalis.model.shop.card;

import it.polimi.codexnaturalis.model.enumeration.ResourceType;

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

    public ResourceType getColor() {
        return ResourceType.NONE;
    }
}
