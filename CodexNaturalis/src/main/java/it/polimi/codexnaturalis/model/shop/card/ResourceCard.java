package it.polimi.codexnaturalis.model.shop.card;

import it.polimi.codexnaturalis.model.enumeration.ResourceType;
import it.polimi.codexnaturalis.model.player.GamePlayerMap;
import it.polimi.codexnaturalis.model.player.PlayerScoreResource;

import java.util.ArrayList;

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
        return frontCardPoint;
    }

    public void setFrontCardPoint(int frontCardPoint) {
        this.frontCardPoint = frontCardPoint;
    }

    public ResourceType getColor() {
        return backCentralResource;
    }

    @Override
    public boolean checkPlaceableCardCondition(PlayerScoreResource scoreCard) {
        return true;
    }

    @Override
    public ArrayList<ResourceType> getBackResources() {
        ArrayList<ResourceType> temp = new ArrayList<>();
        temp.add(backCentralResource);
        return temp;
    }

    @Override
    public int getFrontCardPointCondition(PlayerScoreResource scoreCard, GamePlayerMap playerMap) {
        return frontCardPoint;
    }
}
