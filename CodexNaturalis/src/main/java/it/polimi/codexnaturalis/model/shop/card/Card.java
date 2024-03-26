package it.polimi.codexnaturalis.model.shop.card;

import it.polimi.codexnaturalis.model.enumeration.ResourceType;

public class Card {
    private String png;
    private ResourceType northResource;
    private ResourceType southResource;
    private ResourceType eastResource;
    private ResourceType westResource;
    private boolean isBack;

    public boolean isBack() {
        return isBack;
    }

    public void setBack(boolean back) {
        isBack = back;
    }

    public ResourceType getNorthResource() {
        return northResource;
    }

    public void setNorthResource(ResourceType northResource) {
        this.northResource = northResource;
    }

    public ResourceType getSouthResource() {
        return southResource;
    }

    public void setSouthResource(ResourceType southResource) {
        this.southResource = southResource;
    }

    public ResourceType getEastResource() {
        return eastResource;
    }

    public void setEastResource(ResourceType eastResource) {
        this.eastResource = eastResource;
    }

    public ResourceType getWestResource() {
        return westResource;
    }

    public void setWestResource(ResourceType westResource) {
        this.westResource = westResource;
    }

    public String getPng() {
        return png;
    }

    public void setPng(String png) {
        this.png = png;
    }
}
