package it.polimi.codexnaturalis.model.shop.card;

import it.polimi.codexnaturalis.model.enumeration.ResourceType;

public abstract class Card {
    protected String png;
    protected ResourceType frontNorthResource;
    protected ResourceType frontSouthResource;
    protected ResourceType frontEastResource;
    protected ResourceType frontWestResource;
    protected boolean isBack;

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

    public ResourceCard createResourceCard(){
        return new ResourceCard();
    }

    public abstract ResourceType getColor();
}
