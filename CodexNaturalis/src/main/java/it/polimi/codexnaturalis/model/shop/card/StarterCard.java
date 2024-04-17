package it.polimi.codexnaturalis.model.shop.card;

import it.polimi.codexnaturalis.model.enumeration.ResourceType;
import it.polimi.codexnaturalis.model.player.GamePlayerMap;
import it.polimi.codexnaturalis.model.player.PlayerScoreResource;

import java.util.ArrayList;

public class StarterCard extends Card{
    ResourceType[] backCentralResource;
    protected ResourceType backNorthResource;
    protected ResourceType backSouthResource;
    protected ResourceType backEastResource;
    protected ResourceType backWestResource;

    public StarterCard(int png, ResourceType frontNorthResource, ResourceType frontSouthResource, ResourceType frontEastResource, ResourceType frontWestResource,
                       ResourceType [] backCentralResource, ResourceType backNorthResource, ResourceType backSouthResource, ResourceType backEastResource, ResourceType backWestResource) {
        super(png, frontNorthResource, frontSouthResource, frontEastResource, frontWestResource);
        this.backCentralResource = backCentralResource;
        this.backNorthResource = backNorthResource;
        this.backSouthResource = backSouthResource;
        this.backEastResource = backEastResource;
        this.backWestResource = backWestResource;
    }

    public ResourceType[] getBackCentralResource() {
        System.out.printf("backCentralResource");
        return backCentralResource;
    }

    public void setBackCentralResource(ResourceType[] backCentralResource) {
        this.backCentralResource = backCentralResource;
    }

    public void setBackWestResource(ResourceType backWestResource) {
        this.backWestResource = backWestResource;
    }

    public ResourceType getColor() {
        return ResourceType.NONE;
    }

    @Override
    public boolean checkPlaceableCardCondition(PlayerScoreResource scoreCard) {
        return true;
    }

    @Override
    public ArrayList<ResourceType> getBackResources() {
        ArrayList<ResourceType> temp = new ArrayList<>();
        temp.add(backNorthResource);
        temp.add(backSouthResource);
        temp.add(backWestResource);
        temp.add(backEastResource);
        return temp;
    }

    @Override
    public int getFrontCardPointCondition(PlayerScoreResource scoreCard, int neightbouringCard) {
        return 0;
    }

    @Override
    protected ResourceType getBackNorthResource() {
        return backNorthResource;
    }

    @Override
    protected ResourceType getBackSouthResource() {
        return backSouthResource;
    }

    @Override
    protected ResourceType getBackEastResource() {
        return backEastResource;
    }

    @Override
    protected ResourceType getBackWestResource() {
        return backWestResource;
    }
}
