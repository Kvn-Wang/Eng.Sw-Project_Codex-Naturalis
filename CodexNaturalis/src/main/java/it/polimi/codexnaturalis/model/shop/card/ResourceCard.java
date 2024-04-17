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
    public int getFrontCardPointCondition(PlayerScoreResource scoreCard, int neightbouringCard) {
        if(isBack) {
            return 0;
        } else {
            return frontCardPoint;
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
